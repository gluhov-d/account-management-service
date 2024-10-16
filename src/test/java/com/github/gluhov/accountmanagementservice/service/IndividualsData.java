package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.model.Individuals;
import com.github.gluhov.dto.IndividualsDto;

import java.util.UUID;

import static com.github.gluhov.accountmanagementservice.service.UsersData.*;

public class IndividualsData {
    public static final UUID INDIVIDUAL_UUID = UUID.fromString("eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee");
    public static final UUID INDIVIDUAL_NOT_FOUND = UUID.fromString("eeeeeeee-eeee-eeee-eeee-eeeeeeeeeee0");

    public static final IndividualsDto newIndividualTestDataDto = IndividualsDto.builder()
            .email("will.crus@example.com")
            .passportNumber("C1234567")
            .phoneNumber("345-123-7777")
            .user(newUserTestDataDto)
            .build();

    public static final Individuals individualTestData = Individuals.builder()
            .id(INDIVIDUAL_UUID)
            .userId(USER_INDIVIDUAL_UUID)
            .user(userIndividualTestData)
            .email("max.mustermann@example.com")
            .phoneNumber("123-456-7890")
            .passportNumber("A1234567")
            .build();

    public static final IndividualsDto individualsTestDataDto = IndividualsDto.builder()
            .id(INDIVIDUAL_UUID)
            .email(individualTestData.getEmail())
            .passportNumber(individualTestData.getPassportNumber())
            .phoneNumber(individualTestData.getPhoneNumber())
            .user(userIndividualTestDataDto)
            .userId(USER_INDIVIDUAL_UUID)
            .build();
}