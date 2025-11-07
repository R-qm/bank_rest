package com.bank.bank_rest.bankcards.service;

import com.bank.bank_rest.bankcards.model.dto.request.CardNumRequestDto;
import com.bank.bank_rest.bankcards.model.entity.Cards;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CardService {

    Cards registerCard(Authentication authentication);
    void deleteCard(Authentication authentication, Long id);
    Cards choiceCard(CardNumRequestDto cardNumber);
    void blockCard(Authentication authentication, Long id);
    List<Cards> findAllByUser(Authentication authentication);
}
