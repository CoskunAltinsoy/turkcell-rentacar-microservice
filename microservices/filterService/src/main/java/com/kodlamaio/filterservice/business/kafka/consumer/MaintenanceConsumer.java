package com.kodlamaio.filterservice.business.kafka.consumer;

import com.kodlamaio.commonpackage.events.maintenance.MaintenanceCompletedEvent;
import com.kodlamaio.commonpackage.events.maintenance.MaintenanceCreatedEvent;
import com.kodlamaio.commonpackage.events.maintenance.MaintenanceDeletedEvent;
import com.kodlamaio.commonpackage.events.maintenance.MaintenanceUpdatedEvent;
import com.kodlamaio.filterservice.business.abstracts.FilterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaintenanceConsumer {
    private final FilterService filterService;
    @KafkaListener(
            topics = "maintenance-created",
            groupId = "filter-maintenance-create"
    )
    public void consume(MaintenanceCreatedEvent event) {
        var filter = filterService.getByCarId(event.getCarId());
        filter.setState("MAINTENANCE");
        filterService.add(filter);
        log.info("Maintenance created event consumed {}", event);
    }
    @KafkaListener(
            topics = "maintenance-updated",
            groupId = "filter-maintenance-update"
    )
    public void consume(MaintenanceUpdatedEvent event) {
        var filter = filterService.getByCarId(event.getCarId());
        filter.setCarId(event.getCarId());
        filterService.add(filter);
        log.info("Maintenance updated event consumed {}", event);
    }

    @KafkaListener(
            topics = "maintenance-deleted",
            groupId = "filter-maintenance-delete"
    )
    public void consume(MaintenanceDeletedEvent event) {
        var filter = filterService.getByCarId(event.getCarId());
        filter.setState("AVAILABLE");
        filterService.add(filter);
        log.info("Maintenance deleted event consumed {}", event);
    }

    @KafkaListener(
            topics = "maintenance-completed",
            groupId = "filter-maintenance-complete"
    )
    public void consume(MaintenanceCompletedEvent event) {
        var filter = filterService.getByCarId(event.getCarId());
        filter.setState("AVAILABLE");
        filterService.add(filter);
        log.info("Maintenance completed event consumed {}", event);
    }
    //Todo: maintenance update
}
