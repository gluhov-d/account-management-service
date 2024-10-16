package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.model.Countries;
import com.github.gluhov.accountmanagementservice.model.Status;
import com.github.gluhov.dto.CountriesDto;

import java.util.UUID;

public class CountriesData {
    public static final UUID COUNTRY_MERCHANT_UUID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public static final UUID COUNTRY_MERCHANT_MEMBER_UUID = UUID.fromString("22222222-2222-2222-2222-222222222222");
    public static final UUID COUNTRY_INDIVIDUAL_UUID = UUID.fromString("33333333-3333-3333-3333-333333333333");
    public static final UUID COUNTRY_NOT_FOUND_UUID = UUID.fromString("11111111-1111-1111-1111-111111111110");
    public static final Countries countryMerchantTestData = Countries.builder()
            .id(COUNTRY_MERCHANT_UUID)
            .status(Status.ACTIVE)
            .name("United States")
            .alpha2("US")
            .alpha3("USA")
            .build();
    public static final CountriesDto countryMerchantTestDataDto = CountriesDto.builder()
            .id(COUNTRY_MERCHANT_UUID)
            .status(Status.ACTIVE.toString())
            .name(countryMerchantTestData.getName())
            .alpha2(countryMerchantTestData.getAlpha2())
            .alpha3(countryMerchantTestData.getAlpha3())
            .build();

    public static final Countries countryMerchantMemberTestData = Countries.builder()
            .id(COUNTRY_MERCHANT_MEMBER_UUID)
            .status(Status.ACTIVE)
            .name("Canada")
            .alpha2("CA")
            .alpha3("CAN")
            .build();
    public static final CountriesDto countryMerchantMemberTestDataDto = CountriesDto.builder()
            .id(COUNTRY_MERCHANT_MEMBER_UUID)
            .status(Status.ACTIVE.toString())
            .name(countryMerchantMemberTestData.getName())
            .alpha2(countryMerchantMemberTestData.getAlpha2())
            .alpha3(countryMerchantMemberTestData.getAlpha3())
            .build();

    public static final Countries countryIndividualTestData = Countries.builder()
            .id(COUNTRY_INDIVIDUAL_UUID)
            .status(Status.ACTIVE)
            .name("Germany")
            .alpha2("DE")
            .alpha3("DEU")
            .build();
    public static final CountriesDto countryIndividualTestDataDto = CountriesDto.builder()
            .id(COUNTRY_INDIVIDUAL_UUID)
            .status(Status.ACTIVE.toString())
            .name(countryIndividualTestData.getName())
            .alpha2(countryIndividualTestData.getAlpha2())
            .alpha3(countryIndividualTestData.getAlpha3())
            .build();

}
