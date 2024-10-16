package com.github.gluhov.accountmanagementservice.rest;

import com.github.gluhov.accountmanagementservice.service.ProfileHistoryService;
import com.github.gluhov.dto.ProfileHistoryDto;
import com.github.gluhov.dto.ProfileHistoryDtoListResponse;
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

import static com.github.gluhov.accountmanagementservice.service.ProfileHistoryData.PROFILE_HISTORY_UUID;
import static com.github.gluhov.accountmanagementservice.service.ProfileHistoryData.profileHistoryTestDataDto;
import static com.github.gluhov.accountmanagementservice.service.UsersData.USER_MERCHANT_UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ProfileHistoryRestControllerV1Test {
    @InjectMocks
    private ProfileHistoryRestControllerV1 profileHistoryRestControllerV1;
    @Mock
    private ProfileHistoryService profileHistoryService;

    @Test
    @DisplayName("Test get info functionality then success response")
    void givenId_whenGetById_thenSuccessResponse() {
        when(profileHistoryService.getById(PROFILE_HISTORY_UUID)).thenReturn(Mono.just(profileHistoryTestDataDto));

        Mono<ResponseEntity<ProfileHistoryDto>> result = (Mono<ResponseEntity<ProfileHistoryDto>>) profileHistoryRestControllerV1.getById(PROFILE_HISTORY_UUID);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    ProfileHistoryDto profileHistoryDto = r.getBody();
                    assertEquals(profileHistoryDto.getProfileType(),profileHistoryTestDataDto.getProfileType());
                    assertEquals(profileHistoryDto.getComment(), profileHistoryTestDataDto.getComment());
                    assertEquals(profileHistoryDto.getReason(), profileHistoryTestDataDto.getReason());
                }).verifyComplete();
    }
    @Test
    @DisplayName("Test get all by profile id functionality then success response")
    void givenProfileId_whenGetAllByProfileId_thenSuccessResponse() {
        when(profileHistoryService.getAllByProfileId(USER_MERCHANT_UUID)).thenReturn(Flux.fromIterable(Collections.singleton(profileHistoryTestDataDto)));

        Mono<ResponseEntity<ProfileHistoryDtoListResponse>> result = (Mono<ResponseEntity<ProfileHistoryDtoListResponse>>) profileHistoryRestControllerV1.getAllByProfileId(USER_MERCHANT_UUID);
        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }
}