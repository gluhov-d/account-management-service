package com.github.gluhov.accountmanagementservice.repository;

import com.github.gluhov.accountmanagementservice.model.MerchantMembersInvitations;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface MerchantMembersInvitationsRepository extends R2dbcRepository<MerchantMembersInvitations, UUID> {

    @Query("SELECT * FROM merchant_members_invitations WHERE merchant_id=?")
    Flux<MerchantMembersInvitations> getAllByMerchantId(UUID uuid);
}
