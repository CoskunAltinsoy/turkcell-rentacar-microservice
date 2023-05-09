package com.kodlamaio.filterservice.repository;

import com.kodlamaio.filterservice.entities.Filter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public class FilterRepository extends MongoRepository<Filter, UUID> {
}
