package com.kodlamaio.filterservice.repository;

import com.kodlamaio.filterservice.entities.Filter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface FilterRepository extends MongoRepository<Filter, UUID> {
    void deleteByCarId(UUID carId);
    void deleteAllByBrandId(UUID brandId);
    void deleteAllByModelId(UUID modelId);
    String deleteById(String id);
    Filter findById(String id);
    Filter findByCarId(UUID carId);
    List<Filter> findAllByBrandId(UUID brandId);
    List<Filter> findAllByModelId(UUID modelId);
    List<Filter> findByModelYearGreaterThanEqual(int modelYear);
    List<Filter> findByModelYearLessThanEqual(int modelYear);
    List<Filter> findByModelYearBetween(int firstModelYear, int lastModelYear);
    List<Filter> findByDailyPriceGreaterThanEqual(double dailyPrice);
    List<Filter> findByDailyPriceLessThanEqual(double dailyPrice);
    List<Filter> findByDailyPriceBetween(double firstDailyPrice, double lastDailyPrice);
    Page<Filter> findAll(Pageable pageable);
}
