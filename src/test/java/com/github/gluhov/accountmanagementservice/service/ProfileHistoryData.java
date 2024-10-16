package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.model.ProfileHistory;
import com.github.gluhov.dto.ProfileHistoryDto;

import java.util.UUID;

import static com.github.gluhov.accountmanagementservice.service.MerchantsData.MERCHANT_UUID;
import static com.github.gluhov.accountmanagementservice.service.UsersData.USER_MERCHANT_UUID;
import static com.github.gluhov.accountmanagementservice.service.UsersData.userMerchantTestDataDto;

public class ProfileHistoryData {
    public static final UUID PROFILE_HISTORY_UUID = UUID.fromString("11111111-2222-3333-4444-555555555555");
    public static final UUID PROFILE_HISTORY_NOT_FOUND_UUID = UUID.fromString("11111111-2222-3333-4444-555555555550");

    public static ProfileHistory profileHistoryTestData = ProfileHistory.builder()
            .id(PROFILE_HISTORY_UUID)
            .profileId(USER_MERCHANT_UUID)
            .profileType("merchant")
            .comment("update merchant")
            .changedValues("{\"first_name\":\"John\", \"first_name_old\":\"Johnny\"}")
            .reason("update")
            .build();
}