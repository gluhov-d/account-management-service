package com.github.gluhov.accountmanagementservice.rest;

import com.github.gluhov.accountmanagementservice.model.Role;
import com.github.gluhov.accountmanagementservice.service.MerchantMembersService;
import com.github.gluhov.dto.MerchantMembersDto;
import com.github.gluhov.dto.MerchantMembersDtoListResponse;
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

import static com.github.gluhov.accountmanagementservice.service.MerchantMembersData.MERCHANT_MEMBER_UUID;
import static com.github.gluhov.accountmanagementservice.service.MerchantMembersData.merchantMemberTestDataDto;
import static com.github.gluhov.accountmanagementservice.service.MerchantsData.MERCHANT_UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class MerchantMembersRestControllerV1Test {
    @InjectMocks
    private MerchantMembersRestControllerV1 merchantMembersRestControllerV1;
    @Mock
    private MerchantMembersService merchantMembersService;

    @Test
    @DisplayName("Test get info functionality then success response")
    void givenId_whenGetById_thenSuccessResponse() {
        when(merchantMembersService.getById(MERCHANT_MEMBER_UUID)).thenReturn(Mono.just(merchantMemberTestDataDto));

        Mono<ResponseEntity<MerchantMembersDto>> result = (Mono<ResponseEntity<MerchantMembersDto>>) merchantMembersRestControllerV1.getById(MERCHANT_MEMBER_UUID);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    MerchantMembersDto merchantMembersDto = r.getBody();
                    assertEquals(merchantMembersDto.getMemberRole(), merchantMembersDto.getMemberRole());
                    assertEquals(merchantMembersDto.getMerchantId(), merchantMembersDto.getMerchantId());
                    assertEquals(merchantMembersDto.getUserId(), merchantMembersDto.getUserId());
                }).verifyComplete();
    }

    @Test
    @DisplayName("Test delete functinality then success response")
    void givenId_whenDeleteById_thenSuccessResponse() {
        when(merchantMembersService.deleteById(MERCHANT_MEMBER_UUID)).thenReturn(Mono.empty());

        Mono<?> result = merchantMembersRestControllerV1.delete(MERCHANT_MEMBER_UUID);
        StepVerifier.create(result)
                .expectNextCount(0).verifyComplete();
    }

    @Test
    @DisplayName("Test create functionality then success response")
    void givenMerchantMemberDto_whenCreate_thenSuccessResponse() {
        when(merchantMembersService.save(any(MerchantMembersDto.class))).thenReturn(Mono.just(merchantMemberTestDataDto));

        Mono<ResponseEntity<MerchantMembersDto>> result = (Mono<ResponseEntity<MerchantMembersDto>>) merchantMembersRestControllerV1.create(merchantMemberTestDataDto);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    MerchantMembersDto merchantMembersDto = r.getBody();
                    assertEquals(merchantMembersDto.getMemberRole(), merchantMembersDto.getMemberRole());
                    assertEquals(merchantMembersDto.getMerchantId(), merchantMembersDto.getMerchantId());
                    assertEquals(merchantMembersDto.getUserId(), merchantMembersDto.getUserId());
                }).verifyComplete();
    }

    @Test
    @DisplayName("Test update functionality then success response")
    void givenMerchantMemberDto_whenUpdate_thenSuccessResponse() {
        merchantMemberTestDataDto.setMemberRole(Role.MERCHANT_USER.name());
        when(merchantMembersService.update(any(MerchantMembersDto.class))).thenReturn(Mono.just(merchantMemberTestDataDto));

        Mono<ResponseEntity<MerchantMembersDto>> result = (Mono<ResponseEntity<MerchantMembersDto>>) merchantMembersRestControllerV1.update(merchantMemberTestDataDto);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    MerchantMembersDto merchantMembersDto = r.getBody();
                    assertEquals(merchantMembersDto.getMemberRole(), merchantMembersDto.getMemberRole());
                    assertEquals(merchantMembersDto.getMerchantId(), merchantMembersDto.getMerchantId());
                    assertEquals(merchantMembersDto.getUserId(), merchantMembersDto.getUserId());
                }).verifyComplete();
    }

    @Test
    @DisplayName("Test get all by merchant id then success response")
    void givenMerchantId_whenGetAllByMerchantId_thenSuccessResponse() {
        when(merchantMembersService.getAllByMerchantId(any())).thenReturn(Flux.fromIterable(Collections.singletonList(merchantMemberTestDataDto)));

        Mono<ResponseEntity<MerchantMembersDtoListResponse>> result = (Mono<ResponseEntity<MerchantMembersDtoListResponse>>) merchantMembersRestControllerV1.getAllByMerchantId(MERCHANT_UUID);
        StepVerifier.create(result)
                .expectNextCount(1).verifyComplete();
    }
}