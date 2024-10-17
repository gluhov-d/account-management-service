package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.model.Addresses;
import com.github.gluhov.accountmanagementservice.repository.AddressesRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.github.gluhov.accountmanagementservice.service.AddressesData.ADDRESS_MERCHANT_UUID;
import static com.github.gluhov.accountmanagementservice.service.AddressesData.addressMerchantTestData;
import static com.github.gluhov.accountmanagementservice.service.CountriesData.COUNTRY_MERCHANT_UUID;
import static com.github.gluhov.accountmanagementservice.service.CountriesData.countryMerchantTestData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class AddressesServiceTest {
    @InjectMocks
    private AddressesService addressesService;
    @Mock
    private AddressesRepository addressesRepository;
    @Mock
    private CountriesService countriesService;
    @Test
    @DisplayName("Test get functionality then success response")
    void givenId_whenGetById_thenSuccessResponse() {
        when(addressesRepository.findById(ADDRESS_MERCHANT_UUID)).thenReturn(Mono.just(addressMerchantTestData));
        when(countriesService.getById(COUNTRY_MERCHANT_UUID)).thenReturn(Mono.just(countryMerchantTestData));

        Mono<Addresses> result = addressesService.getById(ADDRESS_MERCHANT_UUID);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals(r.getAddress(), addressMerchantTestData.getAddress());
                    assertEquals(r.getCity(), addressMerchantTestData.getCity());
                    assertEquals(r.getZipCode(), addressMerchantTestData.getZipCode());
                    assertEquals(r.getState(), addressMerchantTestData.getState());
                }).verifyComplete();
    }
    @Test
    @DisplayName("Test delete functionality then success response")
    void givenId_whenDeleteById_thenSuccessResponse() {
        when(addressesRepository.findById(ADDRESS_MERCHANT_UUID)).thenReturn(Mono.just(addressMerchantTestData));
        when(countriesService.getById(COUNTRY_MERCHANT_UUID)).thenReturn(Mono.just(countryMerchantTestData));
        when(addressesRepository.save(any(Addresses.class))).thenReturn(Mono.just(addressMerchantTestData));

        Mono<?> result = addressesService.deleteById(ADDRESS_MERCHANT_UUID);
        StepVerifier.create(result)
                .expectNextCount(0).verifyComplete();
    }
    @Test
    @DisplayName("Test save functionality then success response")
    void givenAddressData_whenSave_thenSuccessResponse() {
        when(countriesService.getById(COUNTRY_MERCHANT_UUID)).thenReturn(Mono.just(countryMerchantTestData));
        when(addressesRepository.save(any(Addresses.class))).thenReturn(Mono.just(addressMerchantTestData));

        Mono<Addresses> result = addressesService.save(addressMerchantTestData);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals(r.getAddress(), addressMerchantTestData.getAddress());
                    assertEquals(r.getCity(), addressMerchantTestData.getCity());
                    assertEquals(r.getZipCode(), addressMerchantTestData.getZipCode());
                    assertEquals(r.getState(), addressMerchantTestData.getState());
                }).verifyComplete();
    }
    @Test
    @DisplayName("Test update functionality then success response")
    void givenAddressData_whenUpdate_thenSuccessResponse() {
        addressMerchantTestData.setAddress("new address");
        when(countriesService.getById(COUNTRY_MERCHANT_UUID)).thenReturn(Mono.just(countryMerchantTestData));
        when(addressesRepository.save(any(Addresses.class))).thenReturn(Mono.just(addressMerchantTestData));
        when(addressesRepository.findById(ADDRESS_MERCHANT_UUID)).thenReturn(Mono.just(addressMerchantTestData));

        Mono<Addresses> result = addressesService.save(addressMerchantTestData);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals(r.getAddress(), addressMerchantTestData.getAddress());
                    assertEquals(r.getCity(), addressMerchantTestData.getCity());
                    assertEquals(r.getZipCode(), addressMerchantTestData.getZipCode());
                    assertEquals(r.getState(), addressMerchantTestData.getState());
                }).verifyComplete();
    }
}