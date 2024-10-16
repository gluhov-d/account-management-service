package com.github.gluhov.accountmanagementservice.rest;

import com.github.gluhov.accountmanagementservice.service.IndividualsService;
import com.github.gluhov.dto.IndividualsDto;
import com.github.gluhov.dto.IndividualsDtoListResponse;
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

import static com.github.gluhov.accountmanagementservice.service.IndividualsData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class IndividualsRestControllerV1Test {
    @InjectMocks
    private IndividualsRestControllerV1 individualsRestControllerV1;
    @Mock
    private IndividualsService individualsService;

    @Test
    @DisplayName("Test get info functionality then success response")
    void givenId_whenGetById_thenSuccessResponse() {
        when(individualsService.getById(any())).thenReturn(Mono.just(individualsTestDataDto));

        Mono<ResponseEntity<IndividualsDto>> result = (Mono<ResponseEntity<IndividualsDto>>) individualsRestControllerV1.getById(INDIVIDUAL_UUID);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    IndividualsDto individualsDto = r.getBody();
                    assertEquals(individualsDto.getPhoneNumber(), individualsTestDataDto.getPhoneNumber());
                    assertEquals(individualsDto.getPassportNumber(), individualTestData.getPassportNumber());
                    assertEquals(individualsDto.getEmail(), individualsTestDataDto.getEmail());
                }).verifyComplete();
    }

    @Test
    @DisplayName("Test delete functionality then success response")
    void givenId_whenDeleteById_thenSuccessResponse() {
        when(individualsService.deleteById(any())).thenReturn(Mono.empty());

        Mono<?> result = individualsRestControllerV1.delete(INDIVIDUAL_UUID);
        StepVerifier.create(result).expectNextCount(0).verifyComplete();
    }

    @Test
    @DisplayName("Test create functionality then success response")
    void givenIndividualDto_whenCreate_thenSuccessResponse() {
        when(individualsService.save(any(IndividualsDto.class))).thenReturn(Mono.just(individualsTestDataDto));

        Mono<ResponseEntity<IndividualsDto>> result = (Mono<ResponseEntity<IndividualsDto>>) individualsRestControllerV1.create(individualsTestDataDto);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    IndividualsDto savedIndividualDto = r.getBody();
                    assertEquals(savedIndividualDto.getEmail(), individualsTestDataDto.getEmail());
                    assertEquals(savedIndividualDto.getPhoneNumber(), individualsTestDataDto.getPhoneNumber());
                    assertEquals(savedIndividualDto.getPassportNumber(), individualsTestDataDto.getPassportNumber());
                });
    }

    @Test
    @DisplayName("Test update functionality then success response")
    void givenIndividualDto_whenUpdate_thenSuccessResponse() {
        individualsTestDataDto.setEmail("new@example.com");
        when(individualsService.save(any(IndividualsDto.class))).thenReturn(Mono.just(individualsTestDataDto));

        Mono<ResponseEntity<IndividualsDto>> result = (Mono<ResponseEntity<IndividualsDto>>) individualsRestControllerV1.create(individualsTestDataDto);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    IndividualsDto savedIndividualDto = r.getBody();
                    assertEquals(savedIndividualDto.getEmail(), individualsTestDataDto.getEmail());
                    assertEquals(savedIndividualDto.getPhoneNumber(), individualsTestDataDto.getPhoneNumber());
                    assertEquals(savedIndividualDto.getPassportNumber(), individualsTestDataDto.getPassportNumber());
                });
    }

    @Test
    @DisplayName("Test get all individuals functionality then success response")
    void givenEmptyRequest_whenGetAll_thenSuccessResponse() {
        when(individualsService.getAll()).thenReturn(Flux.fromIterable(Collections.singletonList(individualsTestDataDto)));

        Mono<ResponseEntity<IndividualsDtoListResponse>> result = (Mono<ResponseEntity<IndividualsDtoListResponse>>) individualsRestControllerV1.getAll();
        StepVerifier.create(result)
                .expectNextCount(1).verifyComplete();
    }
}