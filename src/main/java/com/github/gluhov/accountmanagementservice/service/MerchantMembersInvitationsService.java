package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.exception.EntityNotFoundException;
import com.github.gluhov.accountmanagementservice.mapper.MerchantMembersInvitationsMapper;
import com.github.gluhov.accountmanagementservice.model.MerchantMembersInvitations;
import com.github.gluhov.accountmanagementservice.model.Status;
import com.github.gluhov.accountmanagementservice.repository.MerchantMembersInvitationsRepository;
import com.github.gluhov.dto.MerchantMembersInvitationsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MerchantMembersInvitationsService {
    private final MerchantMembersInvitationsRepository merchantMembersInvitationsRepository;
    private final MerchantsService merchantsService;
    private final MerchantMembersInvitationsMapper merchantMembersInvitationsMapper;

    public Mono<MerchantMembersInvitationsDto> save(MerchantMembersInvitationsDto merchantMembersInvitationsDto) {
        return merchantMembersInvitationsRepository.save(
                MerchantMembersInvitations.builder()
                        .merchantId(merchantMembersInvitationsDto.getMerchantId())
                        .email(merchantMembersInvitationsDto.getEmail())
                        .expires(merchantMembersInvitationsDto.getExpires())
                        .firstName(merchantMembersInvitationsDto.getFirstName())
                        .lastName(merchantMembersInvitationsDto.getLastName())
                        .build())
                .doOnSuccess(m -> log.info("In save - merchant member invitation: {} saved", m))
                .flatMap(this::constructMerchantMembersInvitationsDto);
    }

    public Mono<MerchantMembersInvitationsDto> getById(UUID uuid) {
        return merchantMembersInvitationsRepository.findById(uuid)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Merchant member invitation not found", "AMS_MERCHANT_MEMBER_INVITATION_NOT_FOUND")))
                .flatMap(this::constructMerchantMembersInvitationsDto);
    }

    public Mono<Void> deleteById(UUID uuid) {
        return merchantMembersInvitationsRepository.findById(uuid)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Merchant member invitation not found", "AMS_MERCHANT_MEMBER_INVITATION_NOT_FOUND")))
                .flatMap(merchantMembersInvitations -> {
                    merchantMembersInvitations.setStatus(Status.ARCHIVED);
                    return merchantMembersInvitationsRepository.save(merchantMembersInvitations)
                            .doOnSuccess(m -> log.info("In delete - merchant member invitation: {} deleted", m));
                }).then();
    }

    public Flux<MerchantMembersInvitationsDto> getAllByMerchantId(UUID uuid) {
        return merchantMembersInvitationsRepository.getAllByMerchantId(uuid)
                .flatMap(this::constructMerchantMembersInvitationsDto);
    }

    private Mono<MerchantMembersInvitationsDto> constructMerchantMembersInvitationsDto(MerchantMembersInvitations merchantMembersInvitations) {
        return merchantsService.getById(merchantMembersInvitations.getMerchantId())
                        .flatMap(savedMerchantDto -> {
                            MerchantMembersInvitationsDto merchantMembersInvitationDto = merchantMembersInvitationsMapper.map(merchantMembersInvitations);
                            merchantMembersInvitationDto.setMerchant(savedMerchantDto);
                            return Mono.just(merchantMembersInvitationDto);
                        });
    }
}