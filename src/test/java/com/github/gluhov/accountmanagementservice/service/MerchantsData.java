package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.model.Merchants;
import com.github.gluhov.dto.MerchantsDto;

import java.util.UUID;

import static com.github.gluhov.accountmanagementservice.service.UsersData.*;

public class MerchantsData {
    public static final UUID MERCHANT_UUID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    public static final UUID MERCHANT_UUID_NOT_FOUND = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa0");
    public static final MerchantsDto newMerchantTestDataDto = MerchantsDto.builder()
            .email("new.email@Example.com")
            .creator(newUserTestDataDto)
            .phoneNumber("234-567-8888")
            .filled(true)
            .companyName("Umbrella Corp")
            .build();
    public static final Merchants merchantTestData = Merchants.builder()
            .id(MERCHANT_UUID)
            .email("acme@example.com")
            .filled(true)
            .phoneNumber("123-456-7890")
            .companyName("Acme Corp")
            .creatorId(USER_MERCHANT_UUID)
            .creator(userMerchantTestData)
            .build();

    public static final MerchantsDto merchantsTestDataDto = MerchantsDto.builder()
            .id(MERCHANT_UUID)
            .email(merchantTestData.getEmail())
            .companyName(merchantTestData.getCompanyName())
            .phoneNumber(merchantTestData.getPhoneNumber())
            .filled(merchantTestData.isFilled())
            .creator(userMerchantTestDataDto)
            .creatorId(merchantTestData.getCreatorId())
            .build();
}