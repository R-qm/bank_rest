package com.bank.bank_rest.bankcards.mapper;

import com.bank.bank_rest.bankcards.model.dto.response.UserResponseDto;
import com.bank.bank_rest.bankcards.model.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toDto(Users users);

    Users toEntity(UserResponseDto users);

}
