package com.github.gluhov.accountmanagementservice.repository;

import com.github.gluhov.accountmanagementservice.model.MerchantMembers;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface MerchantMembersRepository extends R2dbcRepository<MerchantMembers, UUID> {

    @Query("SELECT * FROM merchant_members WHERE merchant_id=:1")
    Flux<MerchantMembers> getAllByMerchantId(UUID uuid);
}