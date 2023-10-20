package org.kata.dto.update;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.kata.dto.AvatarDto;
import org.kata.dto.enums.GenderType;

import java.util.Date;
import java.util.List;

@Data
@Builder
@Jacksonized
public class IndividualUpdateDto {

    private String icp;

    private String name;

    private String surname;

    private String patronymic;

    private String fullName;

    private GenderType gender;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String placeOfBirth;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String countryOfBirth;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date birthDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<DocumentUpdateDto> documents;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ContactMediumUpdateDto> contacts;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AddressUpdateDto> address;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AvatarDto> avatar;
}
