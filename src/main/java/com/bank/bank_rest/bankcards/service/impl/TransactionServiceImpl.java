package com.bank.bank_rest.bankcards.service.impl;

import com.bank.bank_rest.bankcards.model.dto.request.TransferRequestDto;
import com.bank.bank_rest.bankcards.model.entity.Cards;
import com.bank.bank_rest.bankcards.model.entity.Users;
import com.bank.bank_rest.bankcards.repository.CardsRepository;
import com.bank.bank_rest.bankcards.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final CardsRepository cardsRepository;

    @Transactional
    @Override
    public String transfersBetweenUserCards(TransferRequestDto dto) {

        if(dto.getFromCardNumber().equals(dto.getToCardNumber())) {
            throw new IllegalArgumentException("Cannot transfer same card");
        }

        Users currentUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Cards> cards = cardsRepository.findAllByCardNumberInForUpdate(
                Arrays.asList(dto.getFromCardNumber(), dto.getToCardNumber())
        );

        Cards fromCard = cards.stream()
                .filter(c -> c.getCardNumber().equals(dto.getFromCardNumber()))
                .findFirst()
                .orElseThrow(()-> new EntityNotFoundException("Card not found"));

        Cards toCard = cards.stream()
                .filter(c -> c.getCardNumber().equals(dto.getToCardNumber()))
                .findFirst()
                .orElseThrow(()-> new EntityNotFoundException("Card not found"));

        if (!fromCard.getUser().equals(currentUser) || !toCard.getUser().equals(currentUser)){
            throw new IllegalStateException("you can transfer only between your own cards");
        }

        if (fromCard.getBalance().compareTo(dto.getAmount())<0){
            throw new IllegalArgumentException("There is not enough balance on the card");
        }

        fromCard.setBalance(fromCard.getBalance().subtract(dto.getAmount()));
        toCard.setBalance(toCard.getBalance().add(dto.getAmount()));



        return "Your current balances: fromCard = " + fromCard.getBalance() +
                " toCard = " + toCard.getBalance();
    }
}
