package com.bank.bank_rest.bankcards.repository;

import com.bank.bank_rest.bankcards.model.entity.Cards;
import com.bank.bank_rest.bankcards.model.entity.Users;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardsRepository extends JpaRepository<Cards, Long> {
    boolean existsByCardNumber(String cardNumber);


    List<Cards> findCardsByUser(Users user);

    boolean existsByDefaultStatusTrueAndUser(Users user);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c from Cards c WHERE c.cardNumber IN :cardNumbers")
    List<Cards> findAllByCardNumberInForUpdate(List<String> cardNumbers );

    Cards findByIdAndUser(Long id, Users user);


    Cards findCardsByUserAndDefaultStatusTrue(Users user);

    Cards findByUserAndCardNumber(Users user, String cardNumber);

}