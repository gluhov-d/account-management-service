package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.mapper.ProfileHistoryMapper;
import com.github.gluhov.accountmanagementservice.model.ProfileHistory;
import com.github.gluhov.accountmanagementservice.repository.ProfileHistoryRepository;
import com.github.gluhov.dto.ProfileHistoryDto;
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

import static com.github.gluhov.accountmanagementservice.service.ProfileHistoryData.*;
import static com.github.gluhov.accountmanagementservice.service.UsersData.USER_MERCHANT_UUID;
import static com.github.gluhov.accountmanagementservice.service.UsersData.userMerchantTestDataDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ProfileHistoryServiceTest {
    @InjectMocks
    private ProfileHistoryService profileHistoryService;
    @Mock
    private UsersService usersService;
    @Mock
    private ProfileHistoryMapper profileHistoryMapper;
    @Mock
    private ProfileHistoryRepository profileHistoryRepository;
    @Test
    @DisplayName("Test get info by id functionality then success response")
    void givenId_whenGetById_thenSuccessResponse() {
        when(usersService.getById(profileHistoryTestData.getProfileId())).thenReturn(Mono.just(userMerchantTestDataDto));
        when(profileHistoryMapper.map(any(ProfileHistory.class))).thenReturn(profileHistoryTestDataDto);
        when(profileHistoryRepository.findById(PROFILE_HISTORY_UUID)).thenReturn(Mono.just(profileHistoryTestData));

        Mono<ProfileHistoryDto> result = profileHistoryService.getById(PROFILE_HISTORY_UUID);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals(r.getReason(), profileHistoryTestData.getReason());
                    assertEquals(r.getComment(), profileHistoryTestData.getComment());
                    assertEquals(r.getChangedValues(), profileHistoryTestData.getChangedValues());
                }).verifyComplete();
    }

    @Test
    @DisplayName("Test get all by profile id functionality then success response")
    void givenProfileId_whenGetAll_thenSuccessResponse() {
        when(profileHistoryRepository.getAllByProfileId(USER_MERCHANT_UUID)).thenReturn(Flux.fromIterable(Collections.singletonList(profileHistoryTestData)));
        when(usersService.getById(profileHistoryTestData.getProfileId())).thenReturn(Mono.just(userMerchantTestDataDto));
        when(profileHistoryMapper.map(any(ProfileHistory.class))).thenReturn(profileHistoryTestDataDto);

        Flux<ProfileHistoryDto> result = profileHistoryService.getAllByProfileId(USER_MERCHANT_UUID);
        StepVerifier.create(result)
                .expectNextCount(1).verifyComplete();
    }

    @Test
    @DisplayName("Test save functionality then success response")
    void givenProfileHistoryData_whenCreate_thenSuccessResponse() {
        when(profileHistoryRepository.save(any(ProfileHistory.class))).thenReturn(Mono.just(profileHistoryTestData));
        when(usersService.getById(profileHistoryTestData.getProfileId())).thenReturn(Mono.just(userMerchantTestDataDto));
        when(profileHistoryMapper.map(any(ProfileHistory.class))).thenReturn(profileHistoryTestDataDto);

        Mono<ProfileHistory> result = profileHistoryService.save(profileHistoryTestData);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals(r.getReason(), profileHistoryTestData.getReason());
                    assertEquals(r.getComment(), profileHistoryTestData.getComment());
                    assertEquals(r.getChangedValues(), profileHistoryTestData.getChangedValues());
                }).verifyComplete();
    }
}