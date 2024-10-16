package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.exception.EntityNotFoundException;
import com.github.gluhov.accountmanagementservice.model.Addresses;
import com.github.gluhov.accountmanagementservice.repository.AddressesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressesService {
    private final AddressesRepository addressesRepository;
    private final CountriesService countriesService;

    public Mono<Addresses> getById(UUID uuid) {
        return addressesRepository.findById(uuid)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Address not found", "AMS_ADDRESS_NOT_FOUND")))
                .flatMap(savedAddress -> countriesService.getById(savedAddress.getCountryId())
                        .flatMap(savedCountry -> {
                            savedAddress.setCountry(savedCountry);
                            return Mono.just(savedAddress);
                        }));
    }

    public Mono<Addresses> save(Addresses addresses) {
        return countriesService.getById(addresses.getCountryId())
                .flatMap(country -> addressesRepository.save(
                        Addresses.builder()
                                .state(addresses.getState())
                                .zipCode(addresses.getZipCode())
                                .countryId(country.getId())
                                .country(country)
                                .city(addresses.getCity())
                                .address(addresses.getAddress())
                                .build())
                );
    }

    public Mono<Void> deleteById(UUID uuid) {
        log.info("IN delete - address uuid: {} ", uuid);
        return getById(uuid)
                .flatMap(addresses -> {
                    addresses.setArchivedAt(LocalDateTime.now());
                    return addressesRepository.save(addresses).then();
                });
    }

    public Mono<Addresses> update(Addresses addresses) {
        return getById(addresses.getId())
                .flatMap(savedAddress -> countriesService.getById(addresses.getCountryId())
                        .flatMap(newCountry -> addressesRepository.save(
                                Addresses.builder()
                                        .id(savedAddress.getId())
                                        .country(newCountry)
                                        .state(addresses.getState())
                                        .zipCode(addresses.getZipCode())
                                        .city(addresses.getCity())
                                        .countryId(newCountry.getId())
                                        .build()
                        )));
    }
}