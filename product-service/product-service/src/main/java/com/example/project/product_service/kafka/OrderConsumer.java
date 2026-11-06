package com.example.project.product_service.kafka;

import com.example.project.product_service.entity.ProductEntity;
import com.example.project.product_service.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);

    private final ProductRepository productRepository;
    private final KafkaTemplate<String, InventoryStatusEvent> kafkaTemplate;

    @Value("${spring.kafka.inventory.topic:inventory-events}")
    private String inventoryTopic;

    public OrderConsumer(ProductRepository productRepository,
                         KafkaTemplate<String, InventoryStatusEvent> kafkaTemplate) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    @KafkaListener(
            topics = "order-events",
            groupId = "product-group"
    )
    public void consumeOrder(OrderEvent orderEvent) {
        log.info("Received order event - orderId: {}, productId: {}, quantity: {}",
                orderEvent.getOrderId(), orderEvent.getProductId(), orderEvent.getQuantity());

        Integer orderedQuantity = orderEvent.getQuantity();
        if (orderedQuantity == null || orderedQuantity <= 0) {
            log.error("Invalid order quantity for ProductId={}. Requested={}",
                    orderEvent.getProductId(), orderedQuantity);
            publishInventoryRejected(orderEvent, "Invalid order quantity", null);
            return;
        }

        ProductEntity product = productRepository.findById(orderEvent.getProductId()).orElse(null);
        if (product == null) {
            log.error("Product not found for ProductId={}", orderEvent.getProductId());
            publishInventoryRejected(orderEvent, "Product not found", null);
            return;
        }

        int updatedRows = productRepository.decrementStockIfAvailable(orderEvent.getProductId(), orderedQuantity);
        if (updatedRows == 0) {
            ProductEntity latestProduct = productRepository.findById(orderEvent.getProductId()).orElse(product);
            log.error("Insufficient stock for ProductId={}. Available={}, Requested={}",
                    latestProduct.getId(), latestProduct.getQuantity(), orderedQuantity);
            publishInventoryRejected(orderEvent, "Insufficient stock", latestProduct.getQuantity());
            return;
        }

        ProductEntity updatedProduct = productRepository.findById(orderEvent.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found after update: " + orderEvent.getProductId()));

        log.info("Inventory Updated. ProductId={}, Remaining={}",
                updatedProduct.getId(), updatedProduct.getQuantity());

        publishInventoryConfirmed(orderEvent, updatedProduct.getQuantity());
    }

    private void publishInventoryConfirmed(OrderEvent orderEvent, Integer remainingQuantity) {
        InventoryStatusEvent event = new InventoryStatusEvent(
                orderEvent.getOrderId(),
                orderEvent.getProductId(),
                "CONFIRMED",
                null,
                remainingQuantity
        );
        kafkaTemplate.send(inventoryTopic, String.valueOf(orderEvent.getOrderId()), event);
    }

    private void publishInventoryRejected(OrderEvent orderEvent, String reason, Integer remainingQuantity) {
        InventoryStatusEvent event = new InventoryStatusEvent(
                orderEvent.getOrderId(),
                orderEvent.getProductId(),
                "REJECTED",
                reason,
                remainingQuantity
        );
        kafkaTemplate.send(inventoryTopic, String.valueOf(orderEvent.getOrderId()), event);
    }
}