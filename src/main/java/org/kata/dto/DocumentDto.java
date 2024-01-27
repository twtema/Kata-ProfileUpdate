package org.kata.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.kata.dto.enums.DocumentType;

import java.util.Date;

@Data
@Builder
@Jacksonized
@Schema(description = "Document DTO")
public class DocumentDto {

    @Schema(description = "ICP", example = "1234567890")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String icp;

    @Schema(description = "Document Type", example = "RF_PASSPORT")
    private DocumentType documentType;

    @Schema(description = "Document Number", example = "12345")
    private String documentNumber;

    @Schema(description = "Document Serial", example = "123546")
    private String documentSerial;

    @Schema(description = "Issue Date", example = "29.10.2023")
    private Date issueDate;

    @Schema(description = "Expiration Date", example = "01.01.2024")
    private Date expirationDate;

    private boolean actual;

    @Schema(description = "External Date Create or Update", example = "01.01.2024")
    private Date externalDate;
}
