package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.mapper.MerchantsMapper;
import com.github.gluhov.accountmanagementservice.model.Merchants;
import com.github.gluhov.accountmanagementservice.model.ProfileHistory;
import com.github.gluhov.accountmanagementservice.repository.MerchantsRepository;
import com.github.gluhov.dto.MerchantsDto;
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

import static com.github.gluhov.accountmanagementservice.service.MerchantsData.*;
import static com.github.gluhov.accountmanagementservice.service.ProfileHistoryData.profileHistoryTestDataDto;
import static com.github.gluhov.accountmanagementservice.service.UsersData.userMerchantTestDataDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class MerchantsServiceTest {
    @InjectMocks
    private MerchantsService merchantsService;
    @Mock
    private MerchantsRepository merchantsRepository;
    @Mock
    private MerchantsMapper merchantsMapper;
    @Mock
    private UsersService usersService;
    @Mock
    private ProfileHistoryService profileHistoryService;

    @Test
    @DisplayName("Test get info functionality then success response")
    void givenId_whenGetById_thenSuccessResponse() {
        when(merchantsRepository.findById(MERCHANT_UUID)).thenReturn(Mono.just(merchantTestData));
        when(usersService.getById(merchantTestData.getCreatorId())).thenReturn(Mono.just(userMerchantTestDataDto));
        when(merchantsMapper.map(any(Merchants.class))).thenReturn(merchantsTestDataDto);

        Mono<MerchantsDto> result = merchantsService.getById(MERCHANT_UUID);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals(r.getCompanyName(), merchantsTestDataDto.getCompanyName());
                    assertEquals(r.getPhoneNumber(), merchantsTestDataDto.getPhoneNumber());
                    assertEquals(r.getEmail(), merchantsTestDataDto.getEmail());
                }).verifyComplete();
    }
    @Test
    @DisplayName("Test delete functionality then success response")
    void givenId_whenDeleteById_thenSuccessResponse() {
        when(merchantsRepository.findById(MERCHANT_UUID)).thenReturn(Mono.just(merchantTestData));
        when(merchantsRepository.save(any(Merchants.class))).thenReturn(Mono.just(merchantTestData));
        when(profileHistoryService.save(any(ProfileHistory.class))).thenReturn(Mono.just(profileHistoryTestDataDto));

        Mono<?> result = merchantsService.deleteById(MERCHANT_UUID);
        StepVerifier.create(result)
                .expectNextCount(0).verifyComplete();
    }
    @Test
    @DisplayName("Test save functionality then success response")
    void givenMerchantDto_whenSave_thenSuccessResponse() {
        when(usersService.save(any(UsersDto.class))).thenReturn(Mono.just(userMerchantTestDataDto));
        when(merchantsRepository.save(any(Merchants.class))).thenReturn(Mono.just(merchantTestData));
        when(merchantsMapper.map(any(Merchants.class))).thenReturn(merchantsTestDataDto);

        Mono<MerchantsDto> result = merchantsService.save(merchantsTestDataDto);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals(r.getEmail(), merchantsTestDataDto.getEmail());
                    assertEquals(r.getPhoneNumber(), merchantsTestDataDto.getPhoneNumber());
                    assertEquals(r.getCompanyName(), merchantsTestDataDto.getCompanyName());
                }).verifyComplete();
    }
    @Test
    @DisplayName("Test update functionality then success response")
    void givenMerchantDto_whenUpdate_thenSuccessResponse() {
        merchantsTestDataDto.setEmail("new@example.com");
        when(usersService.update(any(UsersDto.class))).thenReturn(Mono.just(userMerchantTestDataDto));
        when(merchantsRepository.findById(MERCHANT_UUID)).thenReturn(Mono.just(merchantTestData));
        when(merchantsRepository.save(any(Merchants.class))).thenReturn(Mono.just(merchantTestData));
        when(merchantsMapper.map(any(Merchants.class))).thenReturn(merchantsTestDataDto);
        when(profileHistoryService.save(any(ProfileHistory.class))).thenReturn(Mono.just(profileHistoryTestDataDto));

        Mono<MerchantsDto> result = merchantsService.update(merchantsTestDataDto);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals(r.getEmail(), merchantsTestDataDto.getEmail());
                    assertEquals(r.getPhoneNumber(), merchantsTestDataDto.getPhoneNumber());
                    assertEquals(r.getCompanyName(), merchantsTestDataDto.getCompanyName());
                }).verifyComplete();
    }
    @Test
    @DisplayName("Test get all functionality then success response")
    void givenEmptyRequest_whenGetAll_thenSuccessResponse() {
        when(merchantsRepository.findAll()).thenReturn(Flux.fromIterable(Collections.singletonList(merchantTestData)));
        when(usersService.getById(merchantTestData.getCreatorId())).thenReturn(Mono.just(userMerchantTestDataDto));
        when(merchantsMapper.map(any(Merchants.class))).thenReturn(merchantsTestDataDto);

        Flux<MerchantsDto> result = merchantsService.getAll();
        StepVerifier.create(result)
                .expectNextCount(1).verifyComplete();
    }

}