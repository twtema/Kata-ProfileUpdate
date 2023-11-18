package org.kata.dto.transfer;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.kata.dto.enums.CurrencyType;
import org.kata.dto.enums.OperationStatus;

import java.math.BigDecimal;

@Data
@Builder
@Jacksonized
public class CreditTransferDto {
    private String transferUuid;
    private String walletIdFrom;
    private String walletIdTo;
    private String mobileFrom;
    private String mobileTo;
    private BigDecimal balanceFrom;
    private BigDecimal balanceTo;
    private BigDecimal amount;
    private CurrencyType currency;
    private OperationStatus status;
}
