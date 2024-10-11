package com.github.gluhov.accountmanagementservice.mapper;

import com.github.gluhov.accountmanagementservice.model.Countries;
import com.github.gluhov.dto.CountriesDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountriesMapper {
    CountriesDto map(Countries countries);

    @InheritInverseConfiguration
    Countries map(CountriesDto countriesDto);
}