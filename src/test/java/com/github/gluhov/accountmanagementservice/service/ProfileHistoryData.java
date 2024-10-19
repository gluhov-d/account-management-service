package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.model.ProfileHistory;
import com.github.gluhov.dto.ProfileHistoryDto;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
            .changedValues(createChangedValuesMap())
            .reason("update")
            .build();

    private static Map<String, Object> createChangedValuesMap() {
        Map<String, Object> changedValues = new HashMap<>();
        changedValues.put("first_name", "John");
        changedValues.put("first_name_old", "Johnny");
        return changedValues;
    }

    public static ProfileHistoryDto profileHistoryTestDataDto = ProfileHistoryDto.builder()
            .id(profileHistoryTestData.getId())
            .profileId(profileHistoryTestData.getProfileId())
            .profileType(profileHistoryTestData.getProfileType())
            .user(userMerchantTestDataDto)
            .comment(profileHistoryTestData.getComment())
            .reason(profileHistoryTestData.getReason())
            .changedValues(profileHistoryTestData.getChangedValues())
            .build();
}