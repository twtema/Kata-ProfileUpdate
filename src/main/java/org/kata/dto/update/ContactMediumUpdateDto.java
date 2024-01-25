package org.kata.dto.update;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.kata.dto.enums.ContactMediumType;
import org.kata.dto.enums.ContactMediumUsageType;


@Data
@Builder
@Jacksonized
public class ContactMediumUpdateDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String icp;

    private ContactMediumType type;

    private ContactMediumUsageType usage;

    private String value;
}
