package com.example.task_management.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.example.task_management.events.UserRegisteredEvent;

@Configuration
@EnableKafka
public class KafkaConfig {

    private final String bootstrapServers = "localhost:9092";


    // =========================================================
    // PRODUCER CONFIGURATION
    // =========================================================

    @Bean
    public ProducerFactory<String, UserRegisteredEvent> producerFactory() {

        Map<String, Object> config = new HashMap<>();

        config.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers
        );

        config.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class
        );

        config.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class
        );

        return new DefaultKafkaProducerFactory<>(config);
    }


    // =========================================================
    // KAFKA TEMPLATE
    // =========================================================

    @Bean
    public KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate() {

        return new KafkaTemplate<>(producerFactory());
    }


    // =========================================================
    // CONSUMER CONFIGURATION
    // =========================================================

    @Bean
    public ConsumerFactory<String, UserRegisteredEvent> consumerFactory() {

        Map<String, Object> config = new HashMap<>();

        config.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers
        );

        config.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "user-group"
        );

        config.put(
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                "earliest"
        );

        config.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );

        config.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class
        );


        // JSON deserializer
        JsonDeserializer<UserRegisteredEvent> deserializer =
                new JsonDeserializer<>(UserRegisteredEvent.class);

        deserializer.addTrustedPackages(
                "com.example.task_management.events"
        );


        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                deserializer
        );
    }


    // =========================================================
    // KAFKA LISTENER CONTAINER FACTORY
    // =========================================================

    @Bean(name = "kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, UserRegisteredEvent>
            kafkaListenerContainerFactory(
                    ConsumerFactory<String, UserRegisteredEvent> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, UserRegisteredEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);

        return factory;
    }
}