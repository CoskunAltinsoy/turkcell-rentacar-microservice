package com.kodlamaio.filterservice.business.kafka.consumer;


import com.kodlamaio.commonpackage.events.rental.RentalCreatedEvent;
import com.kodlamaio.commonpackage.events.rental.RentalDeletedEvent;
import com.kodlamaio.commonpackage.events.rental.RentalUpdatedEvent;
import com.kodlamaio.filterservice.business.abstracts.FilterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalConsumer {
    private final FilterService filterService;
    @KafkaListener(
            topics = "rental-created",
            groupId = "filter-rental-create"
    )
    public void consume(RentalCreatedEvent event) {
        var filter = filterService.getByCarId(event.getCarId());
        filter.setState("RENTED");
        filterService.add(filter);
        log.info("Rental created event consumed {}", event);
    }
    @KafkaListener(
            topics = "rental-updated",
            groupId = "filter-rental-update"
    )
    public void consume(RentalUpdatedEvent event) {
        var filter = filterService.getByCarId(event.getCarId());
        filter.setCarId(event.getCarId());
        filterService.add(filter);
        log.info("Rental updated event consumed {}", event);
    }
    @KafkaListener(
            topics = "rental-deleted",
            groupId = "filter-rental-delete"
    )
    public void consume(RentalDeletedEvent event) {
        var filter = filterService.getByCarId(event.getCarId());
        filter.setState("AVAILABLE");
        filterService.add(filter);
        log.info("Rental deleted event consumed {}", event);
    }
    //Todo:Rental update
}