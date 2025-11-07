package com.bank.bank_rest.bankcards.model.dto.response;

import com.bank.bank_rest.bankcards.model.entity.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDto {
    Long id;
    String email;
    String fullName;
    Role role;
}
