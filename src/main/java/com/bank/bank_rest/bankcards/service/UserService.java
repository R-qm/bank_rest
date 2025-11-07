package com.bank.bank_rest.bankcards.service;

import com.bank.bank_rest.bankcards.model.dto.response.UserResponseDto;
import com.bank.bank_rest.bankcards.model.entity.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {


    Users save (Users user);
    Users create(Users user);
    Users getByEmail(String email);
    UserDetailsService getUserDetailsService();
    UserResponseDto getCurrentUser();
    UserResponseDto getByEmailDto(String email);
    void getAdmin();

}
