package com.github.gluhov.accountmanagementservice.mapper;

import com.github.gluhov.accountmanagementservice.model.Addresses;
import com.github.gluhov.dto.AddressesDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressesMapper {
    AddressesDto map(Addresses addresses);

    @InheritInverseConfiguration
    Addresses map(AddressesDto addressesDto);
}