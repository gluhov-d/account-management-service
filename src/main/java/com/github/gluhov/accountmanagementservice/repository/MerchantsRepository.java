package com.github.gluhov.accountmanagementservice.repository;

import com.github.gluhov.accountmanagementservice.model.Merchants;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface MerchantsRepository extends R2dbcRepository<Merchants, UUID> {
}