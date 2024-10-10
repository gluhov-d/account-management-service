package com.github.gluhov.accountmanagementservice.repository;

import com.github.gluhov.accountmanagementservice.model.Countries;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CountriesRepository extends R2dbcRepository<Countries, UUID> {
    @Query("SELECT * FROM countries c WHERE c.name = :1")
    Mono<Countries> findByName(String name);
}