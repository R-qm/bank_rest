package com.bank.bank_rest.bankcards.service.impl;

import com.bank.bank_rest.bankcards.model.dto.request.CardNumRequestDto;
import com.bank.bank_rest.bankcards.model.entity.Cards;
import com.bank.bank_rest.bankcards.model.entity.Users;
import com.bank.bank_rest.bankcards.model.entity.enums.CardStatus;
import com.bank.bank_rest.bankcards.repository.CardsRepository;
import com.bank.bank_rest.bankcards.repository.UsersRepository;
import com.bank.bank_rest.bankcards.service.CardService;
import com.bank.bank_rest.bankcards.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final UserService userService;
    private final CardsRepository cardsRepository;



    @Transactional
    @Override
    public Cards registerCard(Authentication authentication) {

        Users user = userService.getByEmail(authentication.getName());

        String cardNumber = getValidCardNumber();

        boolean defStatus = (!cardsRepository.existsByDefaultStatusTrueAndUser(user));

        Cards card = Cards.builder()
                .user(user)
                .cardNumber(cardNumber)
                .balance(BigDecimal.ZERO)
                .creatDate(new Date())
                .validityPeriod(new Date(System.currentTimeMillis()+1000000000)) //11 дней
                .cardStatus(CardStatus.ACTIVE)
                .defaultStatus(defStatus)
                .build();

        return cardsRepository.save(card) ;
    }

    @Override
    public void deleteCard(Authentication authentication, Long id) {
        Users user = userService.getByEmail(authentication.getName());
        Cards card = cardsRepository.findByIdAndUser(id, user);
        cardsRepository.delete(card);

    }


    @Transactional
    @Override
    public Cards choiceCard( CardNumRequestDto cardNumberRequest) {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        String requestedCardNumber = cardNumberRequest.getCardNumber();
        System.out.println("requestedCardNumber: " + requestedCardNumber);

        // Ищем новую карту, которую пользователь хочет назначить default
        Optional<Cards> newDefault = Optional
                .ofNullable(cardsRepository.findByUserAndCardNumber(user, requestedCardNumber));

        if (newDefault.isEmpty()) {
             throw new  EntityNotFoundException(
                    "Card with number " + requestedCardNumber + " not found for user: " + user.getEmail());
        }
        Cards oldDefault = cardsRepository.findCardsByUserAndDefaultStatusTrue(user);

        if (oldDefault != null && oldDefault.getCardNumber().equals(requestedCardNumber)) {
//            log.info("Card {} is already default for user {}", requestedCardNumber, email);
            return oldDefault;
        }

        if (oldDefault != null) {
            oldDefault.setDefaultStatus(false);
            cardsRepository.save(oldDefault);
//            log.info("Removed default status from card {} for user {}", oldDefault.getCardNumber(), email);
        }

            newDefault.get().setDefaultStatus(true);
        cardsRepository.save(newDefault.get());

//        log.info("Card {} is now default for user {}", requestedCardNumber, email);

        return newDefault.get();
    }


    @Override
    public void blockCard(Authentication authentication, Long id) {
        Users user = userService.getByEmail(authentication.getName());
        Cards card = cardsRepository.findByIdAndUser(id, user);
        card.setCardStatus(CardStatus.BLOCKED);
    }

    @Override
    public List<Cards> findAllByUser(Authentication authentication) {
        Users user = userService.getByEmail(authentication.getName());
        return cardsRepository.findCardsByUser(user);
    }


    private String getValidCardNumber() {
        boolean check = true;
        String cardNumber = "";
        while (check) {
            cardNumber = generateCardNumber();
            if (!existsByCardNumber(cardNumber)) {
                check = false;
            }
        }
        return cardNumber;
    }

    private boolean existsByCardNumber(String cardNumber) {
        return cardsRepository.existsByCardNumber(cardNumber);
    }

    private String generateCardNumber() {
        Random random = new Random();

        long min = 1000000000000000L;
        long max = 9999999999999999L;

        long randNum = (long) (min + random.nextDouble()*(max-min+1));

        return String.valueOf(randNum);
    }
}
