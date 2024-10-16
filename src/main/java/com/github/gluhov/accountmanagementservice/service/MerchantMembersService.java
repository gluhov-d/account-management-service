package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.exception.EntityNotFoundException;
import com.github.gluhov.accountmanagementservice.mapper.MerchantMembersMapper;
import com.github.gluhov.accountmanagementservice.model.MerchantMembers;
import com.github.gluhov.accountmanagementservice.model.ProfileHistory;
import com.github.gluhov.accountmanagementservice.model.Role;
import com.github.gluhov.accountmanagementservice.repository.MerchantMembersRepository;
import com.github.gluhov.accountmanagementservice.util.EntityDtoComparator;
import com.github.gluhov.dto.MerchantMembersDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MerchantMembersService {
    private final MerchantMembersRepository merchantMembersRepository;
    private final MerchantsService merchantsService;
    private final UsersService usersService;
    private final MerchantMembersMapper merchantMembersMapper;
    private final ProfileHistoryService profileHistoryService;

    @Transactional
    public Mono<MerchantMembersDto> save(MerchantMembersDto merchantMembersDto) {
        return merchantsService.getById(merchantMembersDto.getMerchantId())
                .flatMap(savedMerchantDto -> usersService.save(merchantMembersDto.getUser())
                    .flatMap(savedUserDto -> merchantMembersRepository.save(
                            MerchantMembers.builder()
                                    .merchantId(savedMerchantDto.getId())
                                    .memberRole(Role.valueOf(merchantMembersDto.getMemberRole().toUpperCase()))
                                    .userId(savedUserDto.getId())
                                    .build())
                            .doOnSuccess(m -> log.info("In save - merchant member: {} saved", m))
                            .flatMap(savedMerchantMember -> {
                                MerchantMembersDto savedMerchantMemberDto = merchantMembersMapper.map(savedMerchantMember);
                                savedMerchantMemberDto.setUser(savedUserDto);
                                savedMerchantMemberDto.setMerchant(savedMerchantDto);
                                return Mono.just(savedMerchantMemberDto);
                            })
                ));
    }

    @Transactional
    public Mono<MerchantMembersDto> update(MerchantMembersDto merchantMembersDto) {
        return merchantMembersRepository.findById(merchantMembersDto.getId())
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Merchant member not found", "AMS_MERCHANT_MEMBER_NOT_FOUND")))
                .flatMap(savedMerchantMember -> merchantsService.getById(merchantMembersDto.getMerchantId())
                        .flatMap(savedMerchantDto -> usersService.update(merchantMembersDto.getUser())
                                .flatMap(updatedUserDto -> merchantMembersRepository.save(
                                        MerchantMembers.builder()
                                                .id(savedMerchantMember.getId())
                                                .merchantId(savedMerchantDto.getId())
                                                .memberRole(Role.valueOf(merchantMembersDto.getMemberRole().toUpperCase()))
                                                .userId(updatedUserDto.getId())
                                                .build())
                                        .doOnSuccess(m -> log.info("In update - merchant member: {} updated", m))
                                        .flatMap(updatedMerchantMember -> {
                                            MerchantMembersDto updatedMerchantMemberDto = merchantMembersMapper.map(updatedMerchantMember);
                                            updatedMerchantMemberDto.setMerchant(savedMerchantDto);
                                            updatedMerchantMemberDto.setUser(updatedUserDto);
                                            return profileHistoryService.save(
                                                    ProfileHistory.builder()
                                                            .reason("update")
                                                            .profileType("merchant member")
                                                            .comment("update merchant member")
                                                            .profileId(updatedUserDto.getId())
                                                            .changedValues(EntityDtoComparator.findDifferences(savedMerchantMember, updatedMerchantMember))
                                                            .build())
                                                    .flatMap(savedProfileHistory -> Mono.just(updatedMerchantMemberDto));
                                        })
                                )));
    }

    private Mono<MerchantMembersDto> constructMerchantMembersDto(MerchantMembers merchantMembers) {
        return usersService.getById(merchantMembers.getUserId())
                .flatMap(savedUserDto -> merchantsService.getById(merchantMembers.getMerchantId())
                        .flatMap(savedMerchantDto -> {
                            MerchantMembersDto merchantMembersDto = merchantMembersMapper.map(merchantMembers);
                            merchantMembersDto.setMerchant(savedMerchantDto);
                            merchantMembersDto.setUser(savedUserDto);
                            return Mono.just(merchantMembersDto);
                        }));
    }

    public Mono<MerchantMembersDto> getById(UUID uuid) {
        return merchantMembersRepository.findById(uuid)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Merchant member not found", "AMS_MERCHANT_MEMBER_NOT_FOUND")))
                .map(merchantMembersMapper::map);
    }

    @Transactional
    public Mono<Void> deleteById(UUID uuid) {
        return merchantMembersRepository.findById(uuid)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Merchant member not found", "AMS_MERCHANT_MEMBER_NOT_FOUND")))
                .flatMap(merchantMembers -> usersService.deleteById(merchantMembers.getUserId())
                        .flatMap(deletedUser -> merchantMembersRepository.save(merchantMembers)
                                .doOnSuccess(m -> log.info("In delete - merchant member: {} deleted", m))
                                .flatMap(savedMerchantMember -> profileHistoryService.save(
                                        ProfileHistory.builder()
                                                .profileId(merchantMembers.getUserId())
                                                .reason("delete")
                                                .comment("delete merchant member")
                                                .profileType("merchant member")
                                                .changedValues("status:ARCHIVED;archivedAt:" + merchantMembers.getUser().getArchivedAt() + ";")
                                                .build()
                                )).then()));
    }

    public Flux<MerchantMembersDto> getAllByMerchantId(UUID uuid) {
        return merchantMembersRepository.getAllActiveByMerchantId(uuid)
                .flatMap(this::constructMerchantMembersDto);
    }
}