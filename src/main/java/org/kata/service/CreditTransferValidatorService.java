package org.kata.service;

import org.kata.dto.transfer.CreditTransferRequest;

public interface CreditTransferValidatorService {
    void process(CreditTransferRequest data);
}
