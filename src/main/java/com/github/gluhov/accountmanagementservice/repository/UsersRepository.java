package com.github.gluhov.accountmanagementservice.repository;

import com.github.gluhov.accountmanagementservice.model.Users;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface UsersRepository extends R2dbcRepository<Users, UUID> {
}