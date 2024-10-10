package com.github.gluhov.accountmanagementservice.repository;

import com.github.gluhov.accountmanagementservice.model.Individuals;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface IndividualsRepository extends R2dbcRepository<Individuals, UUID> {
}