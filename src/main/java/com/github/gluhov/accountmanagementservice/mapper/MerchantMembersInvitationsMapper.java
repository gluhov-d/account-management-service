package com.github.gluhov.accountmanagementservice.mapper;

import com.github.gluhov.accountmanagementservice.model.MerchantMembersInvitations;
import com.github.gluhov.dto.MerchantMembersInvitationsDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MerchantMembersInvitationsMapper {
    MerchantMembersInvitationsDto map(MerchantMembersInvitations merchantMembersInvitations);

    @InheritInverseConfiguration
    MerchantMembersInvitations map(MerchantMembersInvitationsDto merchantMembersInvitationsDto);
}