package com.bank.bank_rest.bankcards.controller;

import com.bank.bank_rest.bankcards.model.dto.request.TransferRequestDto;
import com.bank.bank_rest.bankcards.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transferOwnCard")
    public ResponseEntity<String> transferOwnCard(@RequestBody @Valid TransferRequestDto dto){
        return new ResponseEntity<>(transactionService.transfersBetweenUserCards(dto), HttpStatus.OK);
    }
}
