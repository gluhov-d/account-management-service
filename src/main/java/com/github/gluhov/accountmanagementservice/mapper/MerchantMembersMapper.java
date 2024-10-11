package com.github.gluhov.accountmanagementservice.mapper;

import com.github.gluhov.accountmanagementservice.model.MerchantMembers;
import com.github.gluhov.dto.MerchantMembersDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MerchantMembersMapper {
    MerchantMembersDto map(MerchantMembers merchantMembers);

    @InheritInverseConfiguration
    MerchantMembers map(MerchantMembersDto merchantMembersDto);
}
