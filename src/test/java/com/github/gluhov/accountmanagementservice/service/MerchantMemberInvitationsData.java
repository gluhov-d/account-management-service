package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.model.MerchantMembersInvitations;
import com.github.gluhov.accountmanagementservice.model.Status;
import com.github.gluhov.dto.MerchantMembersInvitationsDto;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.github.gluhov.accountmanagementservice.service.MerchantsData.*;

public class MerchantMemberInvitationsData {
    public static final UUID MERCHANT_MEMBER_INVITATION_UUID = UUID.fromString("bbbbbbbb-cccc-dddd-eeee-ffffffffffff");
    public static final UUID MERCHANT_MEMBER_INVITATION_NOT_FOUND_UUID = UUID.fromString("bbbbbbbb-cccc-dddd-eeee-fffffffffff0");
    public static final MerchantMembersInvitations merchantMemberInvitationTestData = MerchantMembersInvitations.builder()
            .id(MERCHANT_MEMBER_INVITATION_UUID)
            .merchantId(MERCHANT_UUID)
            .merchant(merchantTestData)
            .status(Status.ACTIVE)
            .lastName("Jordan")
            .firstName("Michael")
            .email("mjordan@example.com")
            .build();

    public static final MerchantMembersInvitationsDto merchantMemberInvitationTestDataDto = MerchantMembersInvitationsDto.builder()
            .merchant(merchantsTestDataDto)
            .merchantId(MERCHANT_UUID)
            .email(merchantMemberInvitationTestData.getEmail())
            .firstName(merchantMemberInvitationTestData.getFirstName())
            .lastName(merchantMemberInvitationTestData.getLastName())
            .status(Status.ACTIVE.name())
            .expires(merchantMemberInvitationTestData.getExpires())
            .build();
    public static final MerchantMembersInvitationsDto newMerchantMemberInvitationTestDataDto = MerchantMembersInvitationsDto.builder()
            .merchant(merchantsTestDataDto)
            .merchantId(MERCHANT_UUID)
            .email("new@example.com")
            .firstName("Sara")
            .lastName("Conor")
            .expires(LocalDateTime.MAX)
            .status(Status.ACTIVE.name())
            .build();
}