package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.mapper.MerchantMembersMapper;
import com.github.gluhov.accountmanagementservice.model.MerchantMembers;
import com.github.gluhov.accountmanagementservice.model.ProfileHistory;
import com.github.gluhov.accountmanagementservice.model.Role;
import com.github.gluhov.accountmanagementservice.repository.MerchantMembersRepository;
import com.github.gluhov.dto.MerchantMembersDto;
import com.github.gluhov.dto.UsersDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

import static com.github.gluhov.accountmanagementservice.service.MerchantMembersData.*;
import static com.github.gluhov.accountmanagementservice.service.MerchantsData.MERCHANT_UUID;
import static com.github.gluhov.accountmanagementservice.service.MerchantsData.merchantsTestDataDto;
import static com.github.gluhov.accountmanagementservice.service.ProfileHistoryData.profileHistoryTestDataDto;
import static com.github.gluhov.accountmanagementservice.service.UsersData.userMerchantMemberTestDataDto;
import static com.github.gluhov.accountmanagementservice.service.UsersData.userMerchantTestDataDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class MerchantMembersServiceTest {
    @InjectMocks
    private MerchantMembersService merchantMembersService;
    @Mock
    private MerchantMembersRepository merchantMembersRepository;
    @Mock
    private MerchantMembersMapper merchantMembersMapper;
    @Mock
    private MerchantsService merchantsService;
    @Mock
    private UsersService usersService;
    @Mock
    private ProfileHistoryService profileHistoryService;

    @Test
    @DisplayName("Test get functionality then success response")
    void givenId_whenGetById_thenSuccessResponse() {
        when(merchantMembersRepository.findById(MERCHANT_MEMBER_UUID)).thenReturn(Mono.just(merchantMemberTestData));
        when(merchantMembersMapper.map(any(MerchantMembers.class))).thenReturn(merchantMemberTestDataDto);

        Mono<MerchantMembersDto> result = merchantMembersService.getById(MERCHANT_MEMBER_UUID);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals(r.getMemberRole(), merchantMemberTestDataDto.getMemberRole());
                    assertEquals(r.getMerchantId(), merchantMemberTestDataDto.getMerchantId());
                    assertEquals(r.getUserId(), merchantMemberTestDataDto.getUserId());
                }).verifyComplete();
    }
    @Test
    @DisplayName("Test delete functionality then success response")
    void givenId_whenDeleteById_thenSuccessResponse() {
        when(merchantMembersRepository.findById(MERCHANT_MEMBER_UUID)).thenReturn(Mono.just(merchantMemberTestData));
        when(merchantMembersRepository.save(any(MerchantMembers.class))).thenReturn(Mono.empty());
        when(profileHistoryService.save(any(ProfileHistory.class))).thenReturn(Mono.just(profileHistoryTestDataDto));
        when(usersService.deleteById(any())).thenReturn(Mono.empty());

        Mono<?> result = merchantMembersService.deleteById(MERCHANT_MEMBER_UUID);
        StepVerifier.create(result)
                .expectNextCount(0).verifyComplete();
    }
    @Test
    @DisplayName("Test save functionality then success response")
    void givenMerchantMemberData_whenSave_thenSuccessResponse() {
        when(usersService.save(any(UsersDto.class))).thenReturn(Mono.just(userMerchantMemberTestDataDto));
        when(merchantMembersRepository.findById(MERCHANT_MEMBER_UUID)).thenReturn(Mono.just(merchantMemberTestData));
        when(merchantMembersRepository.save(any(MerchantMembers.class))).thenReturn(Mono.just(merchantMemberTestData));
        when(merchantsService.getById(merchantMemberTestData.getMerchantId())).thenReturn(Mono.just(merchantsTestDataDto));
        when(merchantMembersMapper.map(any(MerchantMembers.class))).thenReturn(merchantMemberTestDataDto);


        Mono<MerchantMembersDto> result = merchantMembersService.save(merchantMemberTestDataDto);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals(r.getMemberRole(), merchantMemberTestDataDto.getMemberRole());
                    assertEquals(r.getMerchantId(), merchantMemberTestDataDto.getMerchantId());
                    assertEquals(r.getUserId(), merchantMemberTestDataDto.getUserId());
                }).verifyComplete();
    }
    @Test
    @DisplayName("Test update functionality then success response")
    void givenMerchantMemberData_whenUpdate_thenSuccessResponse() {
        merchantMemberTestDataDto.setMemberRole(Role.MERCHANT_USER.name());
        when(merchantMembersRepository.findById(MERCHANT_MEMBER_UUID)).thenReturn(Mono.just(merchantMemberTestData));
        when(merchantsService.getById(merchantMemberTestData.getMerchantId())).thenReturn(Mono.just(merchantsTestDataDto));
        when(usersService.update(any(UsersDto.class))).thenReturn(Mono.just(userMerchantMemberTestDataDto));
        when(merchantMembersRepository.save(any(MerchantMembers.class))).thenReturn(Mono.just(merchantMemberTestData));
        when(merchantMembersMapper.map(any(MerchantMembers.class))).thenReturn(merchantMemberTestDataDto);
        when(profileHistoryService.save(any(ProfileHistory.class))).thenReturn(Mono.just(profileHistoryTestDataDto));

        Mono<MerchantMembersDto> result = merchantMembersService.update(merchantMemberTestDataDto);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals(r.getMemberRole(), merchantMemberTestDataDto.getMemberRole());
                    assertEquals(r.getMerchantId(), merchantMemberTestDataDto.getMerchantId());
                    assertEquals(r.getUserId(), merchantMemberTestDataDto.getUserId());
                }).verifyComplete();
    }
    @Test
    @DisplayName("Test get all by merchant id functionality then success response")
    void givenMerchantId_whenGetAllByMerchantId_thenSuccessResponse() {
        when(merchantMembersRepository.getAllActiveByMerchantId(MERCHANT_UUID)).thenReturn(Flux.fromIterable(Collections.singletonList(merchantMemberTestData)));
        when(usersService.getById(merchantMemberTestData.getUserId())).thenReturn(Mono.just(userMerchantTestDataDto));
        when(merchantsService.getById(merchantMemberTestData.getMerchantId())).thenReturn(Mono.just(merchantsTestDataDto));
        when(merchantMembersMapper.map(any(MerchantMembers.class))).thenReturn(merchantMemberTestDataDto);

        Flux<MerchantMembersDto> result = merchantMembersService.getAllByMerchantId(MERCHANT_UUID);
        StepVerifier.create(result)
                .expectNextCount(1).verifyComplete();
    }

}