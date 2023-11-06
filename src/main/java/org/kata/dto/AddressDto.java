package org.kata.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@Schema(description = "Address DTO")
public class AddressDto {

    @Schema(description = "ICP", example = "1234567890")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String icp;

    @Schema(description = "Street", example = "Pushkin street")
    private String street;

    @Schema(description = "City", example = "Moscow")
    private String city;

    @Schema(description = "State", example = "Russian Federation")
    private String state;

    @Schema(description = "PostCode", example = "198754")
    private String postCode;

    @Schema(description = "Country", example = "Russia")
    private String country;

}
