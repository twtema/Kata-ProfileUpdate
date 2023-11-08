package org.kata.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.kata.dto.enums.GenderType;

import java.util.Date;
import java.util.List;

@Data
@Builder
@Jacksonized
@Schema(description = "Individual DTO")
public class IndividualDto {

    @Schema(description = "ICP", example = "1234567890")
    private String icp;

    @Schema(description = "Name", example = "Roman")
    private String name;

    @Schema(description = "Surname", example = "Shatilov")
    private String surname;

    @Schema(description = "Patronymic", example = "Alexandrovich")
    private String patronymic;

    @Schema(description = "Full name", example = "Shatilov Roman Alexandrovich")
    private String fullName;

    @Schema(description = "Gender", example = "Woman")
    private GenderType gender;

    @Schema(description = "Place of birth", example = "Saint-Petersburg")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String placeOfBirth;

    @Schema(description = "Country of birth", example = "Russia")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String countryOfBirth;

    @Schema(description = "Birth date", example = "21.07.1992")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date birthDate;

    @Schema(description = "List Documents")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<DocumentDto> documents;

    @Schema(description = "List Contacts")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ContactMediumDto> contacts;

    @Schema(description = "List Addresses")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AddressDto> address;

    @Schema(description = "List Avatars")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AvatarDto> avatar;

//    @Schema(description = "List SavingsAccount")
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private List<SavingsAccount> savingsAccount;
}
