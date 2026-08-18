package com.example.task_management.service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.task_management.events.UserRegisteredEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConsumerService {

    @KafkaListener(
        topics = "user-registered",
        groupId = "user-group"
    )
    public void consume(UserRegisteredEvent event) {

        log.info(
            "Register event for email {} has been triggered",
            event.getEmail()
        );

        System.out.println(
            "User Registered: " + event.getEmail()
        );
    }
}