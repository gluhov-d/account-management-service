package com.github.gluhov.accountmanagementservice.mapper;

import com.github.gluhov.accountmanagementservice.model.Merchants;
import com.github.gluhov.dto.MerchantsDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MerchantsMapper {
    MerchantsDto map(Merchants merchants);

    @InheritInverseConfiguration
    Merchants map(MerchantsDto merchantsDto);
}
