package org.kata.service;

import org.kata.dto.ContactMediumDto;
import org.kata.dto.update.ContactMediumUpdateDto;

import java.util.List;

public interface ContactMediumService {
    List<ContactMediumDto> getActualContactMedium(String icp);

    List<ContactMediumDto> updateContact(ContactMediumUpdateDto dto);
}
