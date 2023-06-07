package com.kodlamaio.filterservice.business.abstracts;

import com.kodlamaio.filterservice.business.dto.responses.GetAllFiltersResponse;
import com.kodlamaio.filterservice.business.dto.responses.GetFilterResponse;
import com.kodlamaio.filterservice.entities.Filter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface FilterService {
    List<GetAllFiltersResponse> getAll();
    GetFilterResponse getById(String id);
    void add(Filter filter);
    void delete(String id);
    void deleteAllByBrandId(UUID brandId);
    void deleteAllByModelId(UUID modelId);
    void deleteByCarId(UUID carId);
    List<Filter> getAllByBrandId(UUID brandId);
    List<Filter> getAllByModelId(UUID modelId);
    Filter getByCarId(UUID carId);

    List<GetAllFiltersResponse> getByModelYearGreaterThan(int modelYear);
    List<GetAllFiltersResponse> getByModelYearLessThan(int modelYear);
    List<GetAllFiltersResponse> getByModelYearBetween(int firstModelYear, int lastModelYear);
    List<GetAllFiltersResponse> getByDailyPriceGreaterThan(double dailyPrice);
    List<GetAllFiltersResponse> getByDailyPriceLessThan(double dailyPrice);
    List<GetAllFiltersResponse> getByDailyPriceBetween(double  firstDailyPrice, double lastDailyPrice);
    public Map<String, Object> getAll(int page, int size, String sortBy);
}
