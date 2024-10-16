package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.model.Addresses;
import com.github.gluhov.dto.AddressesDto;

import java.util.UUID;

import static com.github.gluhov.accountmanagementservice.service.CountriesData.*;

public class AddressesData {
    public static final UUID ADDRESS_MERCHANT_UUID =  UUID.fromString("44444444-4444-4444-4444-444444444444");
    public static final UUID ADDRESS_MERCHANT_MEMBER_UUID =  UUID.fromString("55555555-5555-5555-5555-555555555555");
    public static final UUID ADDRESS_INDIVIDUAL_UUID =  UUID.fromString("66666666-6666-6666-6666-666666666666");
    public static final UUID ADDRESS_NOT_FOUND_UUID = UUID.fromString("44444444-4444-4444-4444-444444444440");
    public static final AddressesDto newAddressTestDataDto = AddressesDto.builder()
            .address("2345 Iven Street")
            .city("San Francisco")
            .zipCode("90555")
            .countryId(COUNTRY_MERCHANT_UUID)
            .country(countryMerchantTestDataDto)
            .state("California")
            .build();
    public static final Addresses addressMerchantTestData = Addresses.builder()
            .id(ADDRESS_MERCHANT_UUID)
            .address("1234 Elm Street")
            .countryId(COUNTRY_MERCHANT_UUID)
            .zipCode("90210")
            .city("Los Angeles")
            .state("California")
            .build();
    public static final AddressesDto addressMerchantTestDataDto = AddressesDto.builder()
            .id(ADDRESS_MERCHANT_UUID)
            .address(addressMerchantTestData.getAddress())
            .city(addressMerchantTestData.getCity())
            .zipCode(addressMerchantTestData.getZipCode())
            .country(countryMerchantTestDataDto)
            .countryId(countryMerchantTestDataDto.getId())
            .state(addressMerchantTestData.getState())
            .build();

    public static final Addresses addressMerchantMemberTestData = Addresses.builder()
            .id(ADDRESS_MERCHANT_MEMBER_UUID)
            .address("5678 Maple Avenue")
            .countryId(COUNTRY_MERCHANT_MEMBER_UUID)
            .zipCode("M5H 2N2")
            .city("Toronto")
            .state("Ontario")
            .build();
    public static final AddressesDto addressMerchantMemberTestDataDto = AddressesDto.builder()
            .id(ADDRESS_MERCHANT_MEMBER_UUID)
            .address(addressMerchantMemberTestData.getAddress())
            .city(addressMerchantMemberTestData.getCity())
            .zipCode(addressMerchantMemberTestData.getZipCode())
            .country(countryMerchantMemberTestDataDto)
            .countryId(countryMerchantMemberTestDataDto.getId())
            .state(addressMerchantMemberTestData.getState())
            .build();

    public static final Addresses addressIndividualTestData = Addresses.builder()
            .id(ADDRESS_INDIVIDUAL_UUID)
            .address("1234 Bahnhofstrasse")
            .countryId(COUNTRY_INDIVIDUAL_UUID)
            .zipCode("10115")
            .city("Berlin")
            .state("Berlin")
            .build();
    public static final AddressesDto addressIndividualTestDataDto = AddressesDto.builder()
            .id(ADDRESS_INDIVIDUAL_UUID)
            .address(addressIndividualTestData.getAddress())
            .city(addressIndividualTestData.getCity())
            .zipCode(addressIndividualTestData.getZipCode())
            .country(countryIndividualTestDataDto)
            .countryId(countryIndividualTestDataDto.getId())
            .state(addressIndividualTestData.getState())
            .build();
}