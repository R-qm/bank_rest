package com.bank.bank_rest.bankcards.controller;

import com.bank.bank_rest.bankcards.model.dto.authentification.LoginRequestModel;
import com.bank.bank_rest.bankcards.model.dto.authentification.RegisterRequestModel;
import com.bank.bank_rest.bankcards.service.AuthentificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthentificationService authentificationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestModel request){
        return new ResponseEntity<>(authentificationService.register(request), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestModel loginRequestModel){
        return new ResponseEntity<>(authentificationService.login(loginRequestModel), HttpStatus.OK);
    }




}
