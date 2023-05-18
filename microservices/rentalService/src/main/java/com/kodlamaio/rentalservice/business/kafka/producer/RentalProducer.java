package com.kodlamaio.rentalservice.business.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public void sendMessage(Object event, String topic){
        log.info(String.format("%s event => %s", topic, event.toString()));
        Message<Object> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC,topic)
                .build();

        kafkaTemplate.send(message);
    }
}
