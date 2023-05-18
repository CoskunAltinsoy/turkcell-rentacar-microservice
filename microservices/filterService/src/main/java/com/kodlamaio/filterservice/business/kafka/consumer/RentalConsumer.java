package com.kodlamaio.filterservice.business.kafka.consumer;


import com.kodlamaio.commonpackage.events.rentel.RentalCreatedEvent;
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
    public void consume(RentalCreatedEvent rentalCreatedEvent) {
        var filter = filterService.getByCarId(rentalCreatedEvent.getCarId());
        filter.setState("RENTED");
        filterService.add(filter);
        log.info("Rental created event consumed {}", rentalCreatedEvent);
    }
}