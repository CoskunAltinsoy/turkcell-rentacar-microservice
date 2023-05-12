package com.kodlamaio.inventoryservice.kafka.producer;

import com.kodlamaio.commonpackage.events.inventory.BrandDeletedEvent;
import com.kodlamaio.commonpackage.events.inventory.CarCreatedEvent;
import com.kodlamaio.commonpackage.events.inventory.CarDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class InventoryProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public InventoryProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /*public void sendMessage(CarCreatedEvent carCreatedEvent){
        log.info(String.format("car-created event => %s", carCreatedEvent.toString()));
        Message<CarCreatedEvent> message = MessageBuilder
                .withPayload(carCreatedEvent)
                .setHeader(KafkaHeaders.TOPIC,"car-created")
                .build();

        kafkaTemplate.send(message);
    }
    public void sendMessage(CarDeletedEvent carDeletedEvent){
        log.info(String.format("car-deleted event => %s", carDeletedEvent.toString()));
        Message<CarDeletedEvent> message = MessageBuilder
                .withPayload(carDeletedEvent)
                .setHeader(KafkaHeaders.TOPIC,"car-deleted")
                .build();

        kafkaTemplate.send(message);
    }
    public void sendMessage(BrandDeletedEvent brandDeletedEvent){
        log.info(String.format("car-deleted event => %s", brandDeletedEvent.toString()));
        Message<BrandDeletedEvent> message = MessageBuilder
                .withPayload(brandDeletedEvent)
                .setHeader(KafkaHeaders.TOPIC,"brand-deleted")
                .build();

        kafkaTemplate.send(message);
    }*/

    public void sendMessage(Object event, String topic){
        log.info(String.format("%s event => %s", topic, event.toString()));
        Message<Object> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC,topic)
                .build();

        kafkaTemplate.send(message);
    }
}
