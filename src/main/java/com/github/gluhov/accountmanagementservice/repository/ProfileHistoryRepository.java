package com.github.gluhov.accountmanagementservice.repository;

import com.github.gluhov.accountmanagementservice.model.ProfileHistory;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ProfileHistoryRepository extends R2dbcRepository<ProfileHistory, UUID> {

    @Query("SELECT * FROM profile_history p WHERE p.profile_id = :1")
    Flux<ProfileHistory> getAllByProfileId(UUID id);
}