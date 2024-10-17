package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.model.Countries;
import com.github.gluhov.accountmanagementservice.repository.CountriesRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.github.gluhov.accountmanagementservice.service.CountriesData.COUNTRY_MERCHANT_UUID;
import static com.github.gluhov.accountmanagementservice.service.CountriesData.countryMerchantTestData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CountriesServiceTest {
    @InjectMocks
    private CountriesService countriesService;
    @Mock
    private CountriesRepository countriesRepository;
    @Test
    @DisplayName("Test get functionality then success response")
    void givenId_whenGetById_thenSuccessResponse() {
        when(countriesRepository.findById(COUNTRY_MERCHANT_UUID)).thenReturn(Mono.just(countryMerchantTestData));

        Mono<Countries> result = countriesService.getById(COUNTRY_MERCHANT_UUID);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals(r.getName(), countryMerchantTestData.getName());
                    assertEquals(r.getAlpha2(), countryMerchantTestData.getAlpha2());
                    assertEquals(r.getAlpha3(), countryMerchantTestData.getAlpha3());
                }).verifyComplete();
    }
    @Test
    @DisplayName("Test delete functionality then success response")
    void givenId_whenDeleteById_thenSuccessResponse() {
        when(countriesRepository.findById(COUNTRY_MERCHANT_UUID)).thenReturn(Mono.just(countryMerchantTestData));
        when(countriesRepository.save(any(Countries.class))).thenReturn(Mono.just(countryMerchantTestData));

        Mono<?> result = countriesService.deleteById(COUNTRY_MERCHANT_UUID);
        StepVerifier.create(result)
                .expectNextCount(0).verifyComplete();

    }
}