package com.github.gluhov.accountmanagementservice.mapper;

import com.github.gluhov.accountmanagementservice.model.Individuals;
import com.github.gluhov.dto.IndividualsDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IndividualsMapper {
    IndividualsDto map(Individuals individuals);

    @InheritInverseConfiguration
    Individuals map(IndividualsDto individualsDto);
}