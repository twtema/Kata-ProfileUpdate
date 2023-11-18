package org.kata.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.kata.dto.enums.CurrencyType;

import java.math.BigDecimal;

@Data
@Builder
@Jacksonized
@Schema(description = "DTO представляющий кошелёк")
public class WalletDto {
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Individual ICP", example = "1234567890")
    private String icp;

    @Schema(description = "Идентификатор кошелька", example = "1234567890")
    private String walletId;

    @Schema(description = "Валюта", example = "BYN")
    private CurrencyType currency;

    @Schema(description = "Баланс", example = "123.45")
    private BigDecimal balance;

}
