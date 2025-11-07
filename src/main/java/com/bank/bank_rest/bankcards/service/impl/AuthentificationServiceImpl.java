package com.bank.bank_rest.bankcards.service.impl;

import com.bank.bank_rest.bankcards.config.EncoderConfig;
import com.bank.bank_rest.bankcards.model.dto.authentification.AuthentificationResponseModel;
import com.bank.bank_rest.bankcards.model.dto.authentification.LoginRequestModel;
import com.bank.bank_rest.bankcards.model.dto.authentification.RegisterRequestModel;
import com.bank.bank_rest.bankcards.model.entity.Users;
import com.bank.bank_rest.bankcards.model.entity.enums.Role;
import com.bank.bank_rest.bankcards.repository.UsersRepository;
import com.bank.bank_rest.bankcards.security.JwtService;
import com.bank.bank_rest.bankcards.service.AuthentificationService;
import com.bank.bank_rest.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthentificationServiceImpl implements AuthentificationService {

    private final UserService userService;
    private final EncoderConfig encoderConfig;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;


    @Transactional
    @Override
    public AuthentificationResponseModel register(RegisterRequestModel request) {

        var users= Users.builder()
                .fullName(request.getUsername())
                .email(request.getEmail())
                .password(encoderConfig.passwordEncoder().encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .creatDate(new Date())
                .isBlocked(false)
                .build();

        userService.create(users);

        var token = jwtService.generateToken(users);
        return new AuthentificationResponseModel(token);
    }

    @Transactional
    @Override
    public AuthentificationResponseModel login(LoginRequestModel request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        var user = userService
                .getUserDetailsService()
                .loadUserByUsername(request.getEmail());

        var token = jwtService.generateToken(user);

        return new AuthentificationResponseModel(token);
    }


}
