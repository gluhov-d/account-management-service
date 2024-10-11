package com.github.gluhov.accountmanagementservice.mapper;

import com.github.gluhov.accountmanagementservice.model.ProfileHistory;
import com.github.gluhov.dto.ProfileHistoryDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileHistoryMapper {
    ProfileHistoryDto map(ProfileHistory profileHistory);

    @InheritInverseConfiguration
    ProfileHistory map(ProfileHistoryDto profileHistoryDto);
}