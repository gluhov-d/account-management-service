package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.model.Status;
import com.github.gluhov.accountmanagementservice.model.Users;
import com.github.gluhov.dto.UsersDto;

import java.util.UUID;

import static com.github.gluhov.accountmanagementservice.service.AddressesData.*;

public class UsersData {
    public static final UUID USER_MERCHANT_UUID = UUID.fromString("77777777-7777-7777-7777-777777777777");
    public static final UUID USER_MERCHANT_MEMBER_UUID = UUID.fromString("88888888-8888-8888-8888-888888888888");
    public static final UUID USER_INDIVIDUAL_UUID = UUID.fromString("99999999-9999-9999-9999-999999999999");
    public static final UUID USER_UUID_NOT_FOUND = UUID.fromString("77777777-7777-7777-7777-777777777770");

    public static final UsersDto newUserTestDataDto = UsersDto.builder()
            .secretKey("secret235")
            .lastName("Crus")
            .firstName("Will")
            .filled(true)
            .addresses(newAddressTestDataDto)
            .status(Status.ACTIVE.toString())
            .build();

    public static final Users userMerchantTestData = Users.builder()
            .id(USER_MERCHANT_UUID)
            .addressId(ADDRESS_MERCHANT_UUID)
            .addresses(addressMerchantTestData)
            .filled(true)
            .firstName("John")
            .lastName("Doe")
            .secretKey("secret123")
            .build();
    public static final UsersDto userMerchantTestDataDto = UsersDto.builder()
            .id(USER_MERCHANT_UUID)
            .addresses(addressMerchantTestDataDto)
            .addressId(ADDRESS_MERCHANT_UUID)
            .filled(true)
            .firstName(userMerchantTestData.getFirstName())
            .lastName(userMerchantTestData.getLastName())
            .secretKey(userMerchantTestData.getSecretKey())
            .build();

    public static final Users userMerchantMemberTestData = Users.builder()
            .id(USER_MERCHANT_MEMBER_UUID)
            .addressId(ADDRESS_MERCHANT_MEMBER_UUID)
            .addresses(addressMerchantMemberTestData)
            .filled(false)
            .firstName("Jane")
            .lastName("Smith")
            .secretKey("secret456")
            .build();
    public static final UsersDto userMerchantMemberTestDataDto = UsersDto.builder()
            .id(USER_MERCHANT_MEMBER_UUID)
            .addresses(addressMerchantMemberTestDataDto)
            .addressId(ADDRESS_MERCHANT_MEMBER_UUID)
            .filled(false)
            .firstName(userMerchantMemberTestData.getFirstName())
            .lastName(userMerchantMemberTestData.getLastName())
            .secretKey(userMerchantMemberTestData.getSecretKey())
            .build();

    public static final Users userIndividualTestData = Users.builder()
            .id(USER_INDIVIDUAL_UUID)
            .addressId(ADDRESS_INDIVIDUAL_UUID)
            .addresses(addressIndividualTestData)
            .filled(false)
            .firstName("Max")
            .lastName("Mustermann")
            .secretKey("secret789")
            .build();
    public static final UsersDto userIndividualTestDataDto = UsersDto.builder()
            .id(USER_INDIVIDUAL_UUID)
            .addresses(addressIndividualTestDataDto)
            .addressId(ADDRESS_INDIVIDUAL_UUID)
            .filled(false)
            .firstName(userIndividualTestData.getFirstName())
            .lastName(userIndividualTestData.getLastName())
            .secretKey(userIndividualTestData.getSecretKey())
            .build();

}