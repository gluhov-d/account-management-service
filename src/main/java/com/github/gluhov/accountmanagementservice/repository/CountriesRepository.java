package com.github.gluhov.accountmanagementservice.repository;

import com.github.gluhov.accountmanagementservice.model.Countries;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface CountriesRepository extends R2dbcRepository<Countries, UUID> {
}