package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.exception.EntityNotFoundException;
import com.github.gluhov.accountmanagementservice.mapper.MerchantsMapper;
import com.github.gluhov.accountmanagementservice.model.Merchants;
import com.github.gluhov.accountmanagementservice.model.ProfileHistory;
import com.github.gluhov.accountmanagementservice.model.Status;
import com.github.gluhov.accountmanagementservice.repository.MerchantsRepository;
import com.github.gluhov.accountmanagementservice.util.EntityDtoComparator;
import com.github.gluhov.dto.MerchantsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MerchantsService {
    private final MerchantsRepository merchantsRepository;
    private final MerchantsMapper merchantsMapper;
    private final UsersService userService;
    private final ProfileHistoryService profileHistoryService;

    @Transactional
    public Mono<MerchantsDto> save(MerchantsDto merchantsDto) {
        return userService.save(merchantsDto.getCreator())
                .flatMap(savedCreatorDto -> merchantsRepository.save(
                        Merchants.builder()
                                .phoneNumber(merchantsDto.getPhoneNumber())
                                .companyName(merchantsDto.getCompanyName())
                                .email(merchantsDto.getEmail())
                                .creatorId(savedCreatorDto.getId())
                                .filled(merchantsDto.isFilled())
                                .status(Status.ACTIVE)
                                .build())
                        .doOnSuccess(m -> log.info("In save - merchant: {} saved", m))
                        .flatMap(savedMerchants -> {
                            MerchantsDto savedMerchantsDto = merchantsMapper.map(savedMerchants);
                            savedMerchantsDto.setCreator(savedCreatorDto);
                            return Mono.just(savedMerchantsDto);
                        }));
    }

    @Transactional
    public Mono<MerchantsDto> update(MerchantsDto merchantsDto) {
        return merchantsRepository.findById(merchantsDto.getId())
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Merchant not found", "AMS_MERCHANT_NOT_FOUND")))
                .flatMap(savedMerchant -> userService.update(merchantsDto.getCreator())
                        .flatMap(updatedCreatorDto -> merchantsRepository.save(
                                Merchants.builder()
                                        .id(savedMerchant.getId())
                                        .creatorId(updatedCreatorDto.getId())
                                        .phoneNumber(merchantsDto.getPhoneNumber())
                                        .archivedAt(merchantsDto.getArchivedAt())
                                        .updated(LocalDateTime.now())
                                        .email(merchantsDto.getEmail())
                                        .companyName(merchantsDto.getCompanyName())
                                        .filled(merchantsDto.isFilled())
                                        .build())
                                .doOnSuccess(m -> log.info("In update - merchant: {} updated", m))
                                .flatMap(updatedMerchant -> {
                                    MerchantsDto updatedMerchantDto = merchantsMapper.map(updatedMerchant);
                                    updatedMerchantDto.setCreator(updatedCreatorDto);
                                    return profileHistoryService.save(
                                            ProfileHistory.builder()
                                                    .reason("update")
                                                    .profileType("creator")
                                                    .comment("update creator")
                                                    .profileId(updatedCreatorDto.getId())
                                                    .changedValues(EntityDtoComparator.findDifferences(savedMerchant, updatedMerchant))
                                                    .build())
                                            .flatMap(savedProfileHistory -> Mono.just(updatedMerchantDto));
                                })));
    }

    public Mono<MerchantsDto> getById(UUID uuid) {
        return merchantsRepository.findById(uuid)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Merchant not found", "AMS_MERCHANT_NOT_FOUND")))
                .flatMap(this::constructMerchantsDto);
    }

    private Mono<MerchantsDto> constructMerchantsDto(Merchants merchants) {
        return userService.getById(merchants.getCreatorId())
                .flatMap(savedCreatorDto -> {
                    MerchantsDto merchantsDto = merchantsMapper.map(merchants);
                    merchantsDto.setCreator(savedCreatorDto);
                    return Mono.just(merchantsDto);
                });
    }

    public Flux<MerchantsDto> getAll() {
        return merchantsRepository.findAll()
                .flatMap(this::constructMerchantsDto);
    }

    public Mono<Void> deleteById(UUID uuid) {
        return merchantsRepository.findById(uuid)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Merchant not found", "AMS_MERCHANT_NOT_FOUND")))
                .flatMap(merchant -> {
                    merchant.setStatus(Status.ARCHIVED);
                    merchant.setArchivedAt(LocalDateTime.now());
                    return merchantsRepository.save(merchant)
                            .doOnSuccess(m -> log.info("In delete - merchant: {} deleted", m))
                            .flatMap(savedMerchant -> profileHistoryService.save(
                                    ProfileHistory.builder()
                                            .profileId(merchant.getCreatorId())
                                            .reason("delete")
                                            .comment("delete merchant")
                                            .profileType("merchant creator")
                                            .changedValues("status:ARCHIVED;archivedAt:" + merchant.getArchivedAt() + ";")
                                            .build()
                            )).then();
                });
    }
}