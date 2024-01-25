package org.kata.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.kata.dto.enums.ContactMediumType;
import org.kata.dto.enums.ContactMediumUsageType;


@Data
@Builder
@Jacksonized
@Schema(description = "Contact Medium DTO")
public class ContactMediumDto {

    @Schema(description = "ICP", example = "1234567890")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String icp;

    @Schema(description = "Contact Medium Type", example = "EMAIL")
    private ContactMediumType type;

    @Schema(description = "Contact Medium Usage Type", example = "BUSINESS")
    private ContactMediumUsageType usage;

    @Schema(description = "Value", example = "example@gmail.com")
    private String value;
}
