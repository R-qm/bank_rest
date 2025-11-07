package com.bank.bank_rest.bankcards.model.dto.authentification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthentificationResponseModel {
    private String token;
}
