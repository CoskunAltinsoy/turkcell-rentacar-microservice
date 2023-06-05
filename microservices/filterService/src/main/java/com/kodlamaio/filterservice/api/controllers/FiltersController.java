package com.kodlamaio.filterservice.api.controllers;

import com.kodlamaio.filterservice.business.abstracts.FilterService;
import com.kodlamaio.filterservice.business.dto.responses.GetAllFiltersResponse;
import com.kodlamaio.filterservice.business.dto.responses.GetFilterResponse;
import com.kodlamaio.filterservice.entities.Filter;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/filters")
public class FiltersController {
    private final FilterService filterService;

    @GetMapping
    public List<GetAllFiltersResponse> getAll() {
        return filterService.getAll();
    }
    @GetMapping("/{id}")
    public GetFilterResponse getById(@PathVariable String id) {
        return filterService.getById(id);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        filterService.delete(id);
    }
    @GetMapping("/carId/{carId}")
    public Filter getByCarId(@PathVariable UUID carId) {
        return filterService.getByCarId(carId);
    }
}
