package org.kata.service;

import org.kata.dto.AddressDto;
import org.kata.dto.update.AddressUpdateDto;

public interface AddressService {
    AddressDto getActualAddress(String icp, String conversationId);

    AddressDto updateAddress(AddressUpdateDto dto, String conversationId);

}
