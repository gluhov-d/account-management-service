package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.exception.EntityNotFoundException;
import com.github.gluhov.accountmanagementservice.mapper.CountriesMapper;
import com.github.gluhov.accountmanagementservice.model.Countries;
import com.github.gluhov.accountmanagementservice.model.Status;
import com.github.gluhov.accountmanagementservice.repository.CountriesRepository;
import com.github.gluhov.dto.CountriesDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CountriesService {
    private final CountriesRepository countriesRepository;
    private final CountriesMapper countriesMapper;

    public Mono<Countries> findByName(String name) {
        return countriesRepository.findByName(name)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Country not found", "AMS_COUNTRY_NOT_FOUND")));
    }

    public Mono<Countries> getById(UUID uuid) {
        return countriesRepository.findById(uuid)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Country not found", "AMS_COUNTRY_NOT_FOUND")));
    }

    public Mono<Void> deleteById(UUID uuid) {
        return getById(uuid)
                .flatMap(savedCountry -> countriesRepository.save(
                        Countries.builder()
                                .updated(LocalDateTime.now())
                                .status(Status.ARCHIVED)
                                .build()
                ))
                .doOnSuccess(c -> log.info("In delete - country: {} deleted", c))
                .then();
    }

    public Flux<CountriesDto> getAll() {
        return countriesRepository.findAll()
                .map(countriesMapper::map);
    }
}