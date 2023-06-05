package com.kodlamaio.filterservice.business.kafka.consumer;

import com.kodlamaio.commonpackage.events.inventory.BrandDeletedEvent;
import com.kodlamaio.commonpackage.events.inventory.CarCreatedEvent;
import com.kodlamaio.commonpackage.events.inventory.CarDeletedEvent;
import com.kodlamaio.commonpackage.events.inventory.ModelDeletedEvent;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.filterservice.business.abstracts.FilterService;
import com.kodlamaio.filterservice.entities.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryConsumer {
    private final FilterService filterService;
    private final ModelMapperService modelMapperService;

    @KafkaListener(
            topics = "car-created",
            groupId = "car-create"
    )
    public void consume(CarCreatedEvent event) {
        var filter = modelMapperService.forRequest().map(event, Filter.class);
        filterService.add(filter);
        log.info("Car created event consumed {}", event);
    }
    @KafkaListener(
            topics = "car-deleted",
            groupId = "car-delete"
    )
    public void consume(CarDeletedEvent event) {
        filterService.deleteByCarId(event.getCarId());
        log.info("Car deleted event consumed {}", event);
    }
    @KafkaListener(
            topics = "brand-deleted",
            groupId = "brand-delete"
    )
    public void consume(BrandDeletedEvent event) {
        filterService.deleteAllByBrandId(event.getBrandId());
        log.info("Car deleted event consumed {}", event);
    }
    @KafkaListener(
            topics = "model-deleted",
            groupId = "model-delete"
    )
    public void consume(ModelDeletedEvent event) {
        filterService.deleteAllByModelId(event.getModelId());
        log.info("Car deleted event consumed {}", event);
    }
}
