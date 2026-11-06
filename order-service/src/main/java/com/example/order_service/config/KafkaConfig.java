package com.example.order_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${app.kafka.topics.order-events}")
    public String orderTopic;

    @Value("${app.kafka.topics.inventory-events}")
    public String inventoryTopic;

    @Bean
    public NewTopic orderEventsTopic() {
        return TopicBuilder.name(orderTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic inventoryEventsTopic() {
        return TopicBuilder.name(inventoryTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
