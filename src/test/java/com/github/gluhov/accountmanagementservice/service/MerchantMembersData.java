package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.model.MerchantMembers;
import com.github.gluhov.accountmanagementservice.model.Role;
import com.github.gluhov.dto.MerchantMembersDto;

import java.util.UUID;

import static com.github.gluhov.accountmanagementservice.service.MerchantsData.*;
import static com.github.gluhov.accountmanagementservice.service.UsersData.*;

public class MerchantMembersData {
    public static final UUID MERCHANT_MEMBER_UUID = UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd");
    public static final UUID MERCHANT_MEMBER_NOT_FOUND_UUID = UUID.fromString("dddddddd-dddd-dddd-dddd-ddddddddddd0");
    public static final MerchantMembers merchantMemberTestData = MerchantMembers.builder()
            .id(MERCHANT_MEMBER_UUID)
            .merchantId(MERCHANT_UUID)
            .merchant(merchantTestData)
            .memberRole(Role.MERCHANT_ADMIN)
            .user(userMerchantMemberTestData)
            .userId(USER_MERCHANT_MEMBER_UUID)
            .build();
    public static final MerchantMembersDto merchantMemberTestDataDto = MerchantMembersDto.builder()
            .id(MERCHANT_MEMBER_UUID)
            .merchantId(MERCHANT_UUID)
            .merchant(merchantsTestDataDto)
            .userId(USER_MERCHANT_MEMBER_UUID)
            .user(userMerchantMemberTestDataDto)
            .memberRole(Role.MERCHANT_ADMIN.name())
            .build();
    public static final MerchantMembersDto newMerchantMemberTestDataDto = MerchantMembersDto.builder()
            .merchantId(MERCHANT_UUID)
            .merchant(merchantsTestDataDto)
            .memberRole(Role.MERCHANT_USER.name())
            .user(newUserTestDataDto)
            .build();
}