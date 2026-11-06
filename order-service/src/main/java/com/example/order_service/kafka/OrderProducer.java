package com.example.order_service.kafka;

import com.example.order_service.bean.OrderEvent;
import com.example.order_service.config.KafkaConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

    private static final Logger log = LoggerFactory.getLogger(OrderProducer.class);

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private final KafkaConfig kafkaConfig;

    public OrderProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate, KafkaConfig kafkaConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaConfig = kafkaConfig;
    }

    public void sendOrderEvent(OrderEvent event) {
        try {
            log.info("Order Event Sent: {}", event);
            kafkaTemplate.send(kafkaConfig.orderTopic, String.valueOf(event.getOrderId()), event);
        } catch (Exception e) {
            log.error("Failed to publish order event for orderId {}: {}", event.getOrderId(), e.getMessage());
        }
    }
}

