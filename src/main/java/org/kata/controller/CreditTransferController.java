package org.kata.controller;

import lombok.RequiredArgsConstructor;
import org.kata.dto.transfer.CreditTransferRequest;
import org.kata.exception.credit_transfer.InsufficientCreditException;
import org.kata.exception.credit_transfer.InvalidTransferAmountException;
import org.kata.exception.WalletNotFoundException;
import org.kata.service.CreditTransferValidatorService;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/creditTransfer")
public class CreditTransferController {

    private final CreditTransferValidatorService creditTransferValidatorService;

    @PostMapping
    public void transfer (CreditTransferRequest dto) {
        creditTransferValidatorService.process(dto);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WalletNotFoundException.class)
    public ErrorMessage transferHandler(WalletNotFoundException e) {
        return new ErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidTransferAmountException.class)
    public ErrorMessage transferHandler(InvalidTransferAmountException e) {
        return new ErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InsufficientCreditException.class)
    public ErrorMessage transferHandler(InsufficientCreditException e) {
        return new ErrorMessage(e.getMessage());
    }
}
