package com.bank.bank_rest.bankcards.model.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserCreateRequestDto {
    String email;
    String fullName;
    String password;

}
