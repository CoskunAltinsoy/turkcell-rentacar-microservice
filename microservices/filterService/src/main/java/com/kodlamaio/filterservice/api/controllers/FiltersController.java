package com.kodlamaio.filterservice.api.controllers;

import com.kodlamaio.filterservice.business.abstracts.FilterService;
import com.kodlamaio.filterservice.business.dto.responses.GetAllFiltersResponse;
import com.kodlamaio.filterservice.business.dto.responses.GetFilterResponse;
import com.kodlamaio.filterservice.entities.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    @GetMapping("/getByModelYearGreaterThan/{modelYear}")
    public List<GetAllFiltersResponse> getByModelYearGreaterThan(@PathVariable int modelYear) {
        return filterService.getByModelYearGreaterThan(modelYear);
    }
    @GetMapping("/getByModelYearLessThan/{modelYear}")
    public List<GetAllFiltersResponse> getByModelYearLessThan(@PathVariable int modelYear) {
        return filterService.getByModelYearLessThan(modelYear);
    }
    @GetMapping("/getByModelYearBetween/{firstModelYear}/{lastModelYear}")
    public List<GetAllFiltersResponse> getByModelYearContaining(@PathVariable int firstModelYear,
                                                                @PathVariable int lastModelYear) {
        return filterService.getByModelYearBetween(firstModelYear, lastModelYear);
    }
    @GetMapping("/getByDailyPriceGreaterThan/{dailyPrice}")
    public List<GetAllFiltersResponse> getByDailyPriceGreaterThan(@PathVariable double dailyPrice) {
        return filterService.getByDailyPriceGreaterThan(dailyPrice);
    }
    @GetMapping("/getByDailyPriceLessThan/{dailyPrice}")
    public List<GetAllFiltersResponse> getByDailyPriceLessThan(@PathVariable double dailyPrice) {
        return filterService.getByDailyPriceLessThan(dailyPrice);
    }
    @GetMapping("/getByDailyPriceBetween/{firstDailyPrice}/{lastDailyPrice}")
    public List<GetAllFiltersResponse> getByDailyPriceBetween(@PathVariable double firstDailyPrice,
                                                                @PathVariable double lastDailyPrice) {
        return filterService.getByDailyPriceBetween(firstDailyPrice, lastDailyPrice);
    }
    @GetMapping("/pageable")
    public Map<String, Object> getAllPageable(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "3") int size,
                                              @RequestParam(defaultValue = "modelYear") String sortBy) {
        return filterService.getAll(page, size, sortBy);
    }
}
