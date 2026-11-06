package com.example.order_service.kafka;

import com.example.order_service.bean.InventoryStatusEvent;
import com.example.order_service.entity.Order;
import com.example.order_service.entity.OrderStatus;
import com.example.order_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class InventoryEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(InventoryEventConsumer.class);

    private final OrderRepository orderRepository;

    public InventoryEventConsumer(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @KafkaListener(
            topics = "${app.kafka.topics.inventory-events}",
            groupId = "order-group",
            containerFactory = "inventoryKafkaListenerContainerFactory"
    )
    @Transactional
    public void handleInventoryEvent(InventoryStatusEvent event) {
        try {
            if (event == null || event.getOrderId() == null || event.getStatus() == null) {
                log.warn("Received malformed inventory event, ignoring: {}", event);
                return;
            }

            log.info("Received inventory event: {}", event);

            Optional<Order> optOrder = orderRepository.findById(event.getOrderId());
            if (optOrder.isEmpty()) {
                log.warn("Order not found for orderId: {}, ignoring event", event.getOrderId());
                return;
            }

            Order order = optOrder.get();

            // Idempotency: skip if already in final state
            if (order.getStatus() == OrderStatus.CONFIRMED || order.getStatus() == OrderStatus.REJECTED) {
                log.info("Order {} already in final state {}, ignoring duplicate event", order.getId(), order.getStatus());
                return;
            }

            switch (event.getStatus().toUpperCase()) {
                case "CONFIRMED" -> {
                    order.setStatus(OrderStatus.CONFIRMED);
                    orderRepository.save(order);
                    log.info("Order {} CONFIRMED — productId: {}, remainingQty: {}",
                            order.getId(), event.getProductId(), event.getRemainingQuantity());
                }
                case "REJECTED" -> {
                    order.setStatus(OrderStatus.REJECTED);
                    orderRepository.save(order);
                    log.info("Order {} REJECTED — productId: {}, reason: {}",
                            order.getId(), event.getProductId(), event.getReason());
                }
                default -> log.warn("Unknown inventory status '{}' for orderId {}, ignoring",
                        event.getStatus(), event.getOrderId());
            }

        } catch (Exception e) {
            log.error("Error processing inventory event: {}. Event: {}", e.getMessage(), event);
        }
    }
}
