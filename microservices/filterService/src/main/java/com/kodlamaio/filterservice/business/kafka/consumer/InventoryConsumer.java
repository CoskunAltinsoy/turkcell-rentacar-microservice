package com.kodlamaio.filterservice.business.kafka.consumer;

import com.kodlamaio.commonpackage.events.inventory.*;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.filterservice.business.abstracts.FilterService;
import com.kodlamaio.filterservice.entities.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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
            topics = "car-updated",
            groupId = "car-update"
    )
    public void consume(CarUpdatedEvent event) {
        var filter = modelMapperService.forRequest().map(event, Filter.class);
        filterService.add(filter);
        log.info("Car updated event consumed {}", event);
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
            topics = "brand-updated",
            groupId = "brand-update"
    )
    public void consume(BrandUpdatedEvent event) {
        var filters = filterService.getAllByBrandId(event.getId());
        filters.stream().forEach(filter -> {
            filter.setBrandName(event.getName());
            filterService.add(filter);
                });
        log.info("Brand updated event consumed {}", event);
    }
    @KafkaListener(
            topics = "brand-deleted",
            groupId = "brand-delete"
    )
    public void consume(BrandDeletedEvent event) {
        filterService.deleteAllByBrandId(event.getBrandId());
        log.info("Brand deleted event consumed {}", event);
    }
    @KafkaListener(
            topics = "model-updated",
            groupId = "model-update"
    )
    public void consume(ModelUpdatedEvent event) {
        var filters = filterService.getAllByModelId(event.getId());
        filters.stream().forEach(filter -> {
            filter.setModelName(event.getName());
            filter.setBrandId(event.getBrandId());
            filterService.add(filter);
        });
        log.info("Model updated event consumed {}", event);
    }
    @KafkaListener(
            topics = "model-deleted",
            groupId = "model-delete"
    )
    public void consume(ModelDeletedEvent event) {
        filterService.deleteAllByModelId(event.getModelId());
        log.info("Model deleted event consumed {}", event);
    }

}
