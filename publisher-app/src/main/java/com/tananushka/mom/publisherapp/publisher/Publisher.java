package com.tananushka.mom.publisherapp.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class Publisher {
    private final JmsTemplate jmsTemplate;
    @Value("${spring.jms.topic-name}")
    private String topicName;

    @Scheduled(fixedRateString = "${spring.jms.scheduler.fixed-rate}")
    public void publishMessage() {
        String messageContent = String.valueOf(LocalDateTime.now());
        jmsTemplate.convertAndSend(topicName, messageContent, message -> {
            message.setJMSCorrelationID(UUID.randomUUID().toString());
            log.info("The message {} was published {}", message.getJMSCorrelationID(), messageContent);
            return message;
        });
    }
}
