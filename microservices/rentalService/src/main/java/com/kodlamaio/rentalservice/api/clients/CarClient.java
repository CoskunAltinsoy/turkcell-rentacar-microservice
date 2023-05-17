package com.kodlamaio.rentalservice.api.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "inventory-service")
public interface CarClient {
}
