package com.kodlamaio.filterservice.business.concrete;

import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.filterservice.business.abstracts.FilterService;
import com.kodlamaio.filterservice.business.dto.responses.GetAllFiltersResponse;
import com.kodlamaio.filterservice.business.dto.responses.GetFilterResponse;
import com.kodlamaio.filterservice.entities.Filter;
import com.kodlamaio.filterservice.repository.FilterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilterManager implements FilterService {
    private final FilterRepository filterRepository;
    private final ModelMapperService modelMapperService;

    @Override
    public List<GetAllFiltersResponse> getAll() {
        var filters = filterRepository.findAll();
        var response = filters
                .stream()
                .map(filter -> modelMapperService.forResponse().map(filter, GetAllFiltersResponse.class))
                .collect(Collectors.toList());

        return response;
    }

    @Override
    public GetFilterResponse getById(String id) {
        var filter = filterRepository.findById(id);
        var response = modelMapperService.forResponse().map(filter, GetFilterResponse.class);

        return response;
    }

    @Override
    public void add(Filter filter) {
        filterRepository.save(filter);
    }

    @Override
    public void delete(String id) {
        filterRepository.deleteById(id);
    }

    @Override
    public void deleteAllByBrandId(UUID brandId) {
        filterRepository.deleteAllByBrandId(brandId);
    }

    @Override
    public void deleteAllByModelId(UUID modelId) {
        filterRepository.deleteAllByModelId(modelId);
    }

    @Override
    public void deleteByCarId(UUID carId) {
        filterRepository.deleteByCarId(carId);
    }

    @Override
    public List<Filter> getAllByBrandId(UUID brandId) {
        var filters = filterRepository.findAllByBrandId(brandId);
        return filters;
    }

    @Override
    public List<Filter> getAllByModelId(UUID modelId) {
        var filters = filterRepository.findAllByModelId(modelId);
        return filters;
    }

    @Override
    public Filter getByCarId(UUID carId) {
        return filterRepository.findByCarId(carId);
    }

    @Override
    public List<GetAllFiltersResponse> getByModelYearGreaterThan(int modelYear) {
        var filters = filterRepository.findByModelYearGreaterThanEqual(modelYear);
        var responses = filters
                .stream()
                .map(filter -> modelMapperService.forResponse().map(filter, GetAllFiltersResponse.class))
                .collect(Collectors.toList());

        return responses;
    }

    @Override
    public List<GetAllFiltersResponse> getByModelYearLessThan(int modelYear) {
        var filters = filterRepository.findByModelYearLessThanEqual(modelYear);
        var responses = filters
                .stream()
                .map(filter -> modelMapperService.forResponse().map(filter, GetAllFiltersResponse.class))
                .collect(Collectors.toList());

        return responses;
    }

    @Override
    public List<GetAllFiltersResponse> getByModelYearBetween(int firstModelYear, int lastModelYear) {
        var filters = filterRepository.findByModelYearBetween(firstModelYear, lastModelYear);
        var responses = filters
                .stream()
                .map(filter -> modelMapperService.forResponse().map(filter, GetAllFiltersResponse.class))
                .collect(Collectors.toList());

        return responses;
    }

    @Override
    public List<GetAllFiltersResponse> getByDailyPriceGreaterThan(double dailyPrice) {
        var filters = filterRepository.findByDailyPriceGreaterThanEqual(dailyPrice);
        var responses = filters
                .stream()
                .map(filter -> modelMapperService.forResponse().map(filter, GetAllFiltersResponse.class))
                .collect(Collectors.toList());

        return responses;
    }

    @Override
    public List<GetAllFiltersResponse> getByDailyPriceLessThan(double dailyPrice) {
        var filters = filterRepository.findByDailyPriceLessThanEqual(dailyPrice);
        var responses = filters
                .stream()
                .map(filter -> modelMapperService.forResponse().map(filter, GetAllFiltersResponse.class))
                .collect(Collectors.toList());

        return responses;
    }

    @Override
    public List<GetAllFiltersResponse> getByDailyPriceBetween(double firstDailyPrice, double lastDailyPrice) {
        var filters = filterRepository.findByDailyPriceBetween(firstDailyPrice, lastDailyPrice);
        var responses = filters
                .stream()
                .map(filter -> modelMapperService.forResponse().map(filter, GetAllFiltersResponse.class))
                .collect(Collectors.toList());

        return responses;
    }

    @Override
    public Map<String, Object> getAll(int page, int size, String sortBy) {
        Pageable paging = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<Filter> pages = filterRepository.findAll(paging);
        List<Filter> filters = pages.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("filters", filters);
        response.put("currentPage", pages.getNumber());
        response.put("totalItems", pages.getTotalElements());
        response.put("totalPages", pages.getTotalPages());

       return response;
    }
}
