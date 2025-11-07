package com.bank.bank_rest.bankcards.service.impl;

import com.bank.bank_rest.bankcards.mapper.UserMapper;
import com.bank.bank_rest.bankcards.model.dto.response.UserResponseDto;
import com.bank.bank_rest.bankcards.model.entity.Users;
import com.bank.bank_rest.bankcards.model.entity.enums.Role;
import com.bank.bank_rest.bankcards.repository.UsersRepository;
import com.bank.bank_rest.bankcards.service.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final UserMapper userMapper;


    @Override
    public Users save(Users user) {
        return usersRepository.save(user) ;
    }

    @Override
    public Users create(Users user) {

        if (usersRepository.existsByEmail(user.getEmail())) {
            throw new EntityExistsException("Email already exists");
        }

        return save(user);
    }

    @Override
    public Users getByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(
                ()-> new EntityNotFoundException("User with email " + email + " not found")
        );
    }

    @Override
    public UserDetailsService getUserDetailsService() {
        return this::getByEmail;
    }

    @Override
    public UserResponseDto getByEmailDto(String email) {

        Users user = usersRepository.findByEmail(email).orElseThrow(
                ()-> new EntityNotFoundException("User with email " + email + " not found")
        );

        return userMapper.toDto(user);
    }



    @Override
    public UserResponseDto getCurrentUser() {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userMapper.toDto(user);
    }

    @Override
    public void getAdmin() {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setRole(Role.ROLE_ADMIN);
        save(user);

    }
}
