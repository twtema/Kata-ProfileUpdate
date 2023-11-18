package org.kata.dto.transfer;

import lombok.Data;
import org.kata.dto.enums.CurrencyType;

import java.math.BigDecimal;

@Data
public class CreditTransferRequest {

    String from;
    String to;
    CurrencyType currencyType;
    BigDecimal amount;

}
