package com.numeratorx.adaptor.questionpaperadaptor.configs;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        short relicationFactor = 1;
        return new NewTopic("text-questions-processor", 1, relicationFactor);
    }

    @Bean
    public NewTopic topic2() {
        short relicationFactor = 1;
        return new NewTopic("xml-questions-processor", 1, relicationFactor);
    }

    @Bean
    public NewTopic topic3() {
        short relicationFactor = 1;
        return new NewTopic("json-questions-processor", 1, relicationFactor);
    }
}
