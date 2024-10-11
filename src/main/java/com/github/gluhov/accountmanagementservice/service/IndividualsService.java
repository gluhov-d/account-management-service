package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.exception.EntityNotFoundException;
import com.github.gluhov.accountmanagementservice.mapper.IndividualsMapper;
import com.github.gluhov.accountmanagementservice.model.Individuals;
import com.github.gluhov.accountmanagementservice.model.ProfileHistory;
import com.github.gluhov.accountmanagementservice.model.Status;
import com.github.gluhov.accountmanagementservice.repository.IndividualsRepository;
import com.github.gluhov.accountmanagementservice.util.EntityDtoComparator;
import com.github.gluhov.dto.IndividualsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class IndividualsService {
    private final IndividualsRepository individualsRepository;
    private final IndividualsMapper individualsMapper;
    private final UsersService usersService;
    private final ProfileHistoryService profileHistoryService;
    private final AddressesService addressesService;

    @Transactional
    public Mono<IndividualsDto> save(IndividualsDto individualsDto) {
        return usersService.save(individualsDto.getUser())
                .flatMap(savedUserDto -> individualsRepository.save(
                        Individuals.builder()
                                .passportNumber(individualsDto.getPassportNumber())
                                .status(Status.ACTIVE)
                                .userId(savedUserDto.getId())
                                .build())
                        .doOnSuccess(i -> log.info("In save - individual: {} saved", i))
                        .flatMap(savedIndividuals -> {
                            IndividualsDto savedIndividualsDto = individualsMapper.map(savedIndividuals);
                            savedIndividualsDto.setUser(savedUserDto);
                            return Mono.just(savedIndividualsDto);
                        }));
    }

    @Transactional
    public Mono<IndividualsDto> update(IndividualsDto individualsDto) {
        return individualsRepository.findById(individualsDto.getId())
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Individual not found", "AMS_INDIVIDUAL_NOT_FOUND")))
                .flatMap(savedIndividual -> usersService.update(individualsDto.getUser())
                        .flatMap(updatedUserDto -> individualsRepository.save(
                                Individuals.builder()
                                        .id(savedIndividual.getId())
                                        .updated(LocalDateTime.now())
                                        .verifiedAt(individualsDto.getVerifiedAt())
                                        .passportNumber(individualsDto.getPassportNumber())
                                        .verifiedAt(individualsDto.getVerifiedAt())
                                        .build())
                                .doOnSuccess(i -> log.info("In update - individual: {} updated", i))
                                .flatMap(updatedIndividual -> {
                                    IndividualsDto updatedIndividualDto = individualsMapper.map(updatedIndividual);
                                    updatedIndividualDto.setUser(updatedUserDto);
                                    return profileHistoryService.save(
                                            ProfileHistory.builder()
                                                    .reason("update")
                                                    .profileType("individual")
                                                    .comment("update individual")
                                                    .profileId(updatedUserDto.getId())
                                                    .changedValues(EntityDtoComparator.findDifferences(savedIndividual, updatedIndividual))
                                                    .build())
                                            .flatMap(savedProfileHistory -> Mono.just(updatedIndividualDto));
                                })
                        ));
    }

    public Mono<IndividualsDto> getById(UUID uuid) {
        return individualsRepository.findById(uuid)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Individual not found", "AMS_INDIVIDUAL_NOT_FOUND")))
                .flatMap(this::constructIndividualsDto);
    }

    @Transactional
    public Mono<Void> deleteById(UUID uuid) {
        return individualsRepository.findById(uuid)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Individual not found", "AMS_INDIVIDUAL_NOT_FOUND")))
                .flatMap(individual -> {
                    individual.setStatus(Status.ARCHIVED);
                    individual.setArchivedAt(LocalDateTime.now());
                    return individualsRepository.save(individual)
                            .doOnSuccess(i -> log.info("In delete - individual: {} deleted", i))
                            .flatMap(savedIndividual -> profileHistoryService.save(
                                    ProfileHistory.builder()
                                            .profileId(individual.getUserId())
                                            .reason("delete")
                                            .comment("delete individual")
                                            .profileType("individual")
                                            .changedValues("status:ARCHIVED;archivedAt:" + individual.getArchivedAt() + ";")
                                            .build()
                            )).then();
                });
    }

    public Flux<IndividualsDto> getAll() {
        return individualsRepository.findAll()
                .flatMap(this::constructIndividualsDto);
    }

    private Mono<IndividualsDto> constructIndividualsDto(Individuals individuals) {
        return usersService.getById(individuals.getUserId())
                .flatMap(savedUserDto -> addressesService.getById(savedUserDto.getAddresses().getId())
                        .flatMap(address -> {
                            IndividualsDto individualsDto = individualsMapper.map(individuals);
                            individualsDto.setUser(savedUserDto);
                            return Mono.just(individualsDto);
                        }));
    }
}