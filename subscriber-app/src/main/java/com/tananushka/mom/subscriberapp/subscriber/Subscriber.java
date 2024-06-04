package com.tananushka.mom.subscriberapp.subscriber;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Subscriber {
    @JmsListener(
            destination = "${spring.jms.topic-name}",
            containerFactory = "nonDurableListenerFactory"
    )
    public void receiveNonDurableMessage(Message message) throws JMSException {
        String jmsCorrelationID = message.getJMSCorrelationID();
        String body = message.getBody(String.class);
        log.info("Non-durable message received: jmsCorrelationID={}, sent {}", jmsCorrelationID, body);
    }

    @JmsListener(
            destination = "${spring.jms.topic-name}",
            containerFactory = "durableListenerFactory"
    )
    public void receiveDurableMessage(Message message) throws JMSException {
        String jmsCorrelationID = message.getJMSCorrelationID();
        String body = message.getBody(String.class);
        log.info("Durable message received: jmsCorrelationID={}, sent {}", jmsCorrelationID, body);
    }
}

