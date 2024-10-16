package com.github.gluhov.accountmanagementservice.rest;

import com.github.gluhov.accountmanagementservice.service.MerchantMembersInvitationsService;
import com.github.gluhov.dto.MerchantMembersInvitationsDto;
import com.github.gluhov.dto.MerchantMembersInvitationsDtoListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

import static com.github.gluhov.accountmanagementservice.service.MerchantMembersInvitationsData.MERCHANT_MEMBER_INVITATION_UUID;
import static com.github.gluhov.accountmanagementservice.service.MerchantMembersInvitationsData.merchantMemberInvitationTestDataDto;
import static com.github.gluhov.accountmanagementservice.service.MerchantsData.MERCHANT_UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class MerchantMemberInvitationsRestControllerV1Test {
    @InjectMocks
    private MerchantMembersInvitationsRestControllerV1 merchantMembersInvitationsRestControllerV1;
    @Mock
    private MerchantMembersInvitationsService merchantMembersInvitationsService;

    @Test
    @DisplayName("Test get info functionality then success response")
    void givenId_whenGetById_thenSuccessResponse() {
        when(merchantMembersInvitationsService.getById(MERCHANT_MEMBER_INVITATION_UUID)).thenReturn(Mono.just(merchantMemberInvitationTestDataDto));

        Mono<ResponseEntity<MerchantMembersInvitationsDto>> result = (Mono<ResponseEntity<MerchantMembersInvitationsDto>>) merchantMembersInvitationsRestControllerV1.getById(MERCHANT_MEMBER_INVITATION_UUID);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    MerchantMembersInvitationsDto merchantMembersInvitationsDto = r.getBody();
                    assertEquals(merchantMembersInvitationsDto.getEmail(), merchantMemberInvitationTestDataDto.getEmail());
                    assertEquals(merchantMembersInvitationsDto.getFirstName(), merchantMemberInvitationTestDataDto.getFirstName());
                    assertEquals(merchantMembersInvitationsDto.getLastName(), merchantMemberInvitationTestDataDto.getLastName());
                }).verifyComplete();
    }

    @Test
    @DisplayName("Test delete functionality then success response")
    void givenId_whenDeleteById_thenSuccessResponse() {
        when(merchantMembersInvitationsService.deleteById(MERCHANT_MEMBER_INVITATION_UUID)).thenReturn(Mono.empty());

        Mono<?> result = merchantMembersInvitationsRestControllerV1.delete(MERCHANT_MEMBER_INVITATION_UUID);
        StepVerifier.create(result).expectNextCount(0).verifyComplete();
    }

    @Test
    @DisplayName("Test create functionality then success response")
    void givenMerchantMemberInvitationDto_whenCreate_thenSuccessResponse() {
        when(merchantMembersInvitationsService.save(any(MerchantMembersInvitationsDto.class))).thenReturn(Mono.just(merchantMemberInvitationTestDataDto));

        Mono<ResponseEntity<MerchantMembersInvitationsDto>> result = (Mono<ResponseEntity<MerchantMembersInvitationsDto>>) merchantMembersInvitationsRestControllerV1.create(merchantMemberInvitationTestDataDto);
        StepVerifier.create(result)
                .assertNext(r -> {
                    MerchantMembersInvitationsDto merchantMembersInvitationsDto = r.getBody();
                    assertEquals(merchantMembersInvitationsDto.getEmail(), merchantMemberInvitationTestDataDto.getEmail());
                    assertEquals(merchantMembersInvitationsDto.getFirstName(), merchantMemberInvitationTestDataDto.getFirstName());
                    assertEquals(merchantMembersInvitationsDto.getLastName(), merchantMemberInvitationTestDataDto.getLastName());
                }).verifyComplete();
    }

    @Test
    @DisplayName("Test get all by merchant id functionality then success response")
    void givenMerchantId_whenGetAllByMerchantId_thenSuccessResponse() {
        when(merchantMembersInvitationsService.getAllByMerchantId(any())).thenReturn(Flux.fromIterable(Collections.singletonList(merchantMemberInvitationTestDataDto)));

        Mono<ResponseEntity<MerchantMembersInvitationsDtoListResponse>> result = (Mono<ResponseEntity<MerchantMembersInvitationsDtoListResponse>>) merchantMembersInvitationsRestControllerV1.getAllByMerchantId(MERCHANT_UUID);
        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }
}