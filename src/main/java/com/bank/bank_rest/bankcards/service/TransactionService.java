package com.bank.bank_rest.bankcards.service;

import com.bank.bank_rest.bankcards.model.dto.request.TransferRequestDto;

public interface TransactionService {

    String transfersBetweenUserCards (TransferRequestDto transferRequestDto);
}
