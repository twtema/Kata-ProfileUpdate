package org.kata.dto.recognite;


import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.kata.dto.enums.DocumentType;

import java.util.Date;

@Data
@Builder
@Jacksonized
public class RecognizeDocumentDtoNew {

    private String icp;

    private String name;

    private String surname;

    private String patronymic;

    private DocumentType documentType;

    private String documentNumber;

    private String documentSerial;

    private Date issueDate;

    private Date expirationDate;

    private Date externalDate;
}
