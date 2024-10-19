package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.mapper.IndividualsMapper;
import com.github.gluhov.accountmanagementservice.model.Individuals;
import com.github.gluhov.accountmanagementservice.model.ProfileHistory;
import com.github.gluhov.accountmanagementservice.repository.IndividualsRepository;
import com.github.gluhov.dto.IndividualsDto;
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

import static com.github.gluhov.accountmanagementservice.service.AddressesData.addressIndividualTestData;
import static com.github.gluhov.accountmanagementservice.service.IndividualsData.*;
import static com.github.gluhov.accountmanagementservice.service.ProfileHistoryData.profileHistoryTestData;
import static com.github.gluhov.accountmanagementservice.service.UsersData.userIndividualTestDataDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class IndividualsServiceTest {
    @InjectMocks
    private IndividualsService individualsService;
    @Mock
    private IndividualsRepository individualsRepository;
    @Mock
    private IndividualsMapper individualsMapper;
    @Mock
    private UsersService usersService;
    @Mock
    private ProfileHistoryService profileHistoryService;
    @Mock
    private AddressesService addressesService;

    @Test
    @DisplayName("Test get info functionality then success response")
    void givenId_whenGetById_thenSuccessResponse() {
        when(individualsRepository.findById(INDIVIDUAL_UUID)).thenReturn(Mono.just(individualTestData));
        when(usersService.getById(individualTestData.getUserId())).thenReturn(Mono.just(userIndividualTestDataDto));
        when(addressesService.getById(individualTestData.getUser().getAddressId())).thenReturn(Mono.just(addressIndividualTestData));
        when(individualsMapper.map(any(Individuals.class))).thenReturn(individualsTestDataDto);

        Mono<IndividualsDto> result = individualsService.getById(INDIVIDUAL_UUID);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals(r.getPassportNumber(), individualsTestDataDto.getPassportNumber());
                    assertEquals(r.getPhoneNumber(), individualsTestDataDto.getPhoneNumber());
                    assertEquals(r.getEmail(), individualsTestDataDto.getEmail());
                }).verifyComplete();
    }
    @Test
    @DisplayName("Test delete functionality then success response")
    void givenId_whenDeleteById_thenSuccessResponse() {
        when(individualsRepository.findById(INDIVIDUAL_UUID)).thenReturn(Mono.just(individualTestData));
        when(individualsService.getById(INDIVIDUAL_UUID)).thenReturn(Mono.just(individualsTestDataDto));
        when(usersService.getById(individualTestData.getUserId())).thenReturn(Mono.just(userIndividualTestDataDto));
        when(individualsMapper.map(any(Individuals.class))).thenReturn(individualsTestDataDto);

        when(individualsRepository.findById(INDIVIDUAL_UUID)).thenReturn(Mono.just(individualTestData));
        when(individualsRepository.save(any(Individuals.class))).thenReturn(Mono.just(individualTestData));
        when(profileHistoryService.save(any(ProfileHistory.class))).thenReturn(Mono.just(profileHistoryTestData));
        when(usersService.deleteById(individualTestData.getUserId())).thenReturn(Mono.empty());

        Mono<?> result = individualsService.deleteById(INDIVIDUAL_UUID);
        StepVerifier.create(result)
                .expectNextCount(0).verifyComplete();
    }
    @Test
    @DisplayName("Test save functionality then success response")
    void givenIndividualDto_whenSave_thenSuccessResponse() {
        when(usersService.save(any(UsersDto.class))).thenReturn(Mono.just(userIndividualTestDataDto));
        when(individualsRepository.save(any(Individuals.class))).thenReturn(Mono.just(individualTestData));
        when(addressesService.getById(individualTestData.getUser().getAddressId())).thenReturn(Mono.just(addressIndividualTestData));
        when(individualsMapper.map(any(Individuals.class))).thenReturn(individualsTestDataDto);

        Mono<IndividualsDto> result = individualsService.save(individualsTestDataDto);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals(r.getEmail(), individualsTestDataDto.getEmail());
                    assertEquals(r.getPhoneNumber(), individualsTestDataDto.getPhoneNumber());
                    assertEquals(r.getPassportNumber(), individualsTestDataDto.getPassportNumber());
                }).verifyComplete();
    }
    @Test
    @DisplayName("Test update functionality then success response")
    void givenIndividualDto_whenUpdate_thenSuccessResponse() {
        individualsTestDataDto.setEmail("new@example.com");
        when(individualsRepository.findById(INDIVIDUAL_UUID)).thenReturn(Mono.just(individualTestData));
        when(individualsService.getById(INDIVIDUAL_UUID)).thenReturn(Mono.just(individualsTestDataDto));
        when(usersService.getById(individualTestData.getUserId())).thenReturn(Mono.just(userIndividualTestDataDto));
        when(individualsMapper.map(any(Individuals.class))).thenReturn(individualsTestDataDto);

        when(usersService.update(any(UsersDto.class))).thenReturn(Mono.just(userIndividualTestDataDto));
        when(individualsRepository.findById(INDIVIDUAL_UUID)).thenReturn(Mono.just(individualTestData));
        when(individualsRepository.save(any(Individuals.class))).thenReturn(Mono.just(individualTestData));
        when(addressesService.getById(individualTestData.getUser().getAddressId())).thenReturn(Mono.just(addressIndividualTestData));
        when(individualsMapper.map(any(Individuals.class))).thenReturn(individualsTestDataDto);
        when(profileHistoryService.save(any(ProfileHistory.class))).thenReturn(Mono.just(profileHistoryTestData));

        Mono<IndividualsDto> result = individualsService.update(individualsTestDataDto);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals(r.getEmail(), individualsTestDataDto.getEmail());
                    assertEquals(r.getPhoneNumber(), individualsTestDataDto.getPhoneNumber());
                    assertEquals(r.getPassportNumber(), individualsTestDataDto.getPassportNumber());
                }).verifyComplete();
    }
    @Test
    @DisplayName("Test get all functionality then success response")
    void givenEmptyRequest_whenGetAll_thenSuccessResponse() {
        when(individualsRepository.findAll()).thenReturn(Flux.fromIterable(Collections.singletonList(individualTestData)));
        when(usersService.getById(individualTestData.getUserId())).thenReturn(Mono.just(userIndividualTestDataDto));
        when(addressesService.getById(individualTestData.getUser().getAddressId())).thenReturn(Mono.just(addressIndividualTestData));
        when(individualsMapper.map(any(Individuals.class))).thenReturn(individualsTestDataDto);

        Flux<IndividualsDto> result = individualsService.getAll();
        StepVerifier.create(result)
                .expectNextCount(1).verifyComplete();
    }
}