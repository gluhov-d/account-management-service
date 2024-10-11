package com.github.gluhov.accountmanagementservice.mapper;

import com.github.gluhov.accountmanagementservice.model.Users;
import com.github.gluhov.dto.UsersDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    UsersDto map(Users users);

    @InheritInverseConfiguration
    Users map(UsersDto usersDto);
}