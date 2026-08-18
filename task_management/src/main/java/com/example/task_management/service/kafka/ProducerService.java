package com.example.task_management.service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.task_management.events.UserRegisteredEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProducerService {

    private final KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;

    public ProducerService(
            KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserRegisterEvent(UserRegisteredEvent event) {

        kafkaTemplate.send("user-registered", event)
                .whenComplete((result, exception) -> {

                    if (exception != null) {

                        log.error(
                            "Failed to send user registration event",
                            exception
                        );

                        return;
                    }

                    log.info(
                        "User registration event sent successfully. Offset: {}",
                        result.getRecordMetadata().offset()
                    );
                });
    }
}