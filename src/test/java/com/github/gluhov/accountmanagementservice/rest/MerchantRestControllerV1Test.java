package com.github.gluhov.accountmanagementservice.rest;

import com.github.gluhov.accountmanagementservice.service.MerchantsService;
import com.github.gluhov.dto.MerchantsDto;
import com.github.gluhov.dto.MerchantsDtoListResponse;
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

import static com.github.gluhov.accountmanagementservice.service.MerchantsData.MERCHANT_UUID;
import static com.github.gluhov.accountmanagementservice.service.MerchantsData.merchantsTestDataDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class MerchantRestControllerV1Test {
    @InjectMocks
    private MerchantsRestControllerV1 merchantsRestControllerV1;
    @Mock
    private MerchantsService merchantsService;

    @Test
    @DisplayName("Test get info functionality then success response")
    void givenId_whenGetById_thenSuccessResponse() {
        when(merchantsService.getById(MERCHANT_UUID)).thenReturn(Mono.just(merchantsTestDataDto));

        Mono<ResponseEntity<MerchantsDto>> result = (Mono<ResponseEntity<MerchantsDto>>) merchantsRestControllerV1.getById(MERCHANT_UUID);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    MerchantsDto merchantsDto = r.getBody();
                    assertEquals(merchantsDto.getEmail(), merchantsTestDataDto.getEmail());
                    assertEquals(merchantsDto.getPhoneNumber(), merchantsTestDataDto.getPhoneNumber());
                    assertEquals(merchantsDto.getCompanyName(), merchantsTestDataDto.getCompanyName());
                }).verifyComplete();
    }

    @Test
    @DisplayName("Test delete functionality then success response")
    void givenId_whenDeleteById_thenSuccessResponse() {
        when(merchantsService.deleteById(MERCHANT_UUID)).thenReturn(Mono.empty());

        Mono<?> result = merchantsRestControllerV1.delete(MERCHANT_UUID);
        StepVerifier.create(result)
                .expectNextCount(0).verifyComplete();
    }

    @Test
    @DisplayName("Test create functionality then success response")
    void givenMerchantDto_whenCreate_thenSuccessResponse() {
        when(merchantsService.save(any(MerchantsDto.class))).thenReturn(Mono.just(merchantsTestDataDto));

        Mono<ResponseEntity<MerchantsDto>> result = (Mono<ResponseEntity<MerchantsDto>>) merchantsRestControllerV1.create(merchantsTestDataDto);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    MerchantsDto merchantsDto = r.getBody();
                    assertEquals(merchantsDto.getEmail(), merchantsTestDataDto.getEmail());
                    assertEquals(merchantsDto.getPhoneNumber(), merchantsTestDataDto.getPhoneNumber());
                    assertEquals(merchantsDto.getCompanyName(), merchantsTestDataDto.getCompanyName());
                }).verifyComplete();
    }

    @Test
    @DisplayName("Test update functionality then success response")
    void givenMerchantDto_whenUpdate_thenSuccessResponse() {
        merchantsTestDataDto.setEmail("new@example.com");
        when(merchantsService.update(any(MerchantsDto.class))).thenReturn(Mono.just(merchantsTestDataDto));

        Mono<ResponseEntity<MerchantsDto>> result = (Mono<ResponseEntity<MerchantsDto>>) merchantsRestControllerV1.update(merchantsTestDataDto);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    MerchantsDto merchantsDto = r.getBody();
                    assertEquals(merchantsDto.getEmail(), merchantsTestDataDto.getEmail());
                    assertEquals(merchantsDto.getPhoneNumber(), merchantsTestDataDto.getPhoneNumber());
                    assertEquals(merchantsDto.getCompanyName(), merchantsTestDataDto.getCompanyName());
                }).verifyComplete();
    }

    @Test
    @DisplayName("Test get all functionality then success response")
    void getEmptyRequest_whenGetAll_thenSuccessREsponse() {
        when(merchantsService.getAll()).thenReturn(Flux.fromIterable(Collections.singletonList(merchantsTestDataDto)));

        Mono<ResponseEntity<MerchantsDtoListResponse>> result = (Mono<ResponseEntity<MerchantsDtoListResponse>>) merchantsRestControllerV1.getAll();
        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }
}