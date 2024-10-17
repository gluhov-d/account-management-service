package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.mapper.MerchantMembersInvitationsMapper;
import com.github.gluhov.accountmanagementservice.model.MerchantMembersInvitations;
import com.github.gluhov.accountmanagementservice.repository.MerchantMembersInvitationsRepository;
import com.github.gluhov.dto.MerchantMembersInvitationsDto;
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

import static com.github.gluhov.accountmanagementservice.service.MerchantMemberInvitationsData.*;
import static com.github.gluhov.accountmanagementservice.service.MerchantsData.MERCHANT_UUID;
import static com.github.gluhov.accountmanagementservice.service.MerchantsData.merchantsTestDataDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class MerchantMemberInvitationsServiceTest {
    @InjectMocks
    private MerchantMembersInvitationsService merchantMembersInvitationsService;
    @Mock
    private MerchantsService merchantsService;
    @Mock
    private MerchantMembersInvitationsRepository merchantMembersInvitationsRepository;
    @Mock
    private MerchantMembersInvitationsMapper merchantMembersInvitationsMapper;

    @Test
    @DisplayName("Test get info functionality then success response")
    void givenId_whenGetById_thenSuccessResponse() {
        when(merchantMembersInvitationsRepository.findById(MERCHANT_MEMBER_INVITATION_UUID)).thenReturn(Mono.just(merchantMemberInvitationTestData));
        when(merchantsService.getById(merchantMemberInvitationTestData.getMerchantId())).thenReturn(Mono.just(merchantsTestDataDto));
        when(merchantMembersInvitationsMapper.map(any(MerchantMembersInvitations.class))).thenReturn(merchantMemberInvitationTestDataDto);

        Mono<MerchantMembersInvitationsDto> result = merchantMembersInvitationsService.getById(MERCHANT_MEMBER_INVITATION_UUID);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals(r.getLastName(), merchantMemberInvitationTestDataDto.getLastName());
                    assertEquals(r.getFirstName(), merchantMemberInvitationTestDataDto.getFirstName());
                    assertEquals(r.getEmail(), merchantMemberInvitationTestDataDto.getEmail());
                }).verifyComplete();
    }
    @Test
    @DisplayName("Test delete functionality then success response")
    void givenId_whenDeleteById_thenSuccessResponse() {
        when(merchantMembersInvitationsRepository.findById(MERCHANT_MEMBER_INVITATION_UUID)).thenReturn(Mono.just(merchantMemberInvitationTestData));
        when(merchantMembersInvitationsRepository.save(any(MerchantMembersInvitations.class))).thenReturn(Mono.empty());
        when(merchantsService.getById(merchantMemberInvitationTestData.getMerchantId())).thenReturn(Mono.just(merchantsTestDataDto));

        Mono<?> result = merchantMembersInvitationsService.deleteById(MERCHANT_MEMBER_INVITATION_UUID);
        StepVerifier.create(result)
                .expectNextCount(0).verifyComplete();
    }
    @Test
    @DisplayName("Test save functionality then success response")
    void givenMerchantMemberInvitationData_whenSave_thenSuccessResponse() {
        when(merchantMembersInvitationsRepository.save(any(MerchantMembersInvitations.class))).thenReturn(Mono.just(merchantMemberInvitationTestData));
        when(merchantsService.getById(merchantMemberInvitationTestData.getMerchantId())).thenReturn(Mono.just(merchantsTestDataDto));
        when(merchantMembersInvitationsMapper.map(any(MerchantMembersInvitations.class))).thenReturn(merchantMemberInvitationTestDataDto);

        Mono<MerchantMembersInvitationsDto> result = merchantMembersInvitationsService.save(merchantMemberInvitationTestDataDto);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals(r.getLastName(), merchantMemberInvitationTestDataDto.getLastName());
                    assertEquals(r.getFirstName(), merchantMemberInvitationTestDataDto.getFirstName());
                    assertEquals(r.getEmail(), merchantMemberInvitationTestDataDto.getEmail());
                }).verifyComplete();
    }
    @Test
    @DisplayName("Test get all by merchant id functionality then success response")
    void givenMerchantId_whenGetAllByMerchantId_thenSuccessResponse() {
        when(merchantMembersInvitationsRepository.getAllActiveByMerchantId(MERCHANT_UUID)).thenReturn(Flux.fromIterable(Collections.singletonList(merchantMemberInvitationTestData)));
        when(merchantsService.getById(merchantMemberInvitationTestData.getMerchantId())).thenReturn(Mono.just(merchantsTestDataDto));
        when(merchantMembersInvitationsMapper.map(any(MerchantMembersInvitations.class))).thenReturn(merchantMemberInvitationTestDataDto);

        Flux<MerchantMembersInvitationsDto> result = merchantMembersInvitationsService.getAllByMerchantId(MERCHANT_UUID);
        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }
}