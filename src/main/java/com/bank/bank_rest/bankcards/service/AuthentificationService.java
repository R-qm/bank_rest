package com.bank.bank_rest.bankcards.service;

import com.bank.bank_rest.bankcards.model.dto.authentification.AuthentificationResponseModel;
import com.bank.bank_rest.bankcards.model.dto.authentification.LoginRequestModel;
import com.bank.bank_rest.bankcards.model.dto.authentification.RegisterRequestModel;
import com.bank.bank_rest.bankcards.model.entity.Users;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface AuthentificationService {

    AuthentificationResponseModel register (RegisterRequestModel registerRequestModel);

    AuthentificationResponseModel login (LoginRequestModel loginRequestModel);

}
