package com.bank.bank_rest.bankcards.controller;

import com.bank.bank_rest.bankcards.model.dto.request.CardNumRequestDto;
import com.bank.bank_rest.bankcards.model.entity.Cards;
import com.bank.bank_rest.bankcards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    @PostMapping("/reg")
    public ResponseEntity<?> registerCard(Authentication authentication){
        return new ResponseEntity<>(cardService.registerCard(authentication), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getCards(Authentication authentication){
        return new ResponseEntity<>(cardService.findAllByUser(authentication), HttpStatus.OK);
    }

    @PatchMapping("/choiceDef")
    public ResponseEntity<?> choiceCard(@RequestBody CardNumRequestDto requestNum){
        return new ResponseEntity<>(cardService.choiceCard( requestNum), HttpStatus.OK);
    }

}
