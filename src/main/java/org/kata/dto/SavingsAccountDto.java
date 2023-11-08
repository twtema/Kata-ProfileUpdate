package org.kata.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;


@Data
@Builder
@Jacksonized
@Schema(description = "Сберегательный счет")
public class SavingsAccountDto {

    @Schema(description = "ICP", example = "1234567890")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String icp;
    @Schema(description = "Forex", example = "USD")
    private String forex;

    @Schema(description = "InfoOfPercent", example = "19%")
    private String infoOfPercent;

    @Schema(description = "FinalSum", example = "123456789")
    private Long finalSum;


    @Schema(description = "DepositTerm", example = "10")
    private Byte depositTerm;

}
