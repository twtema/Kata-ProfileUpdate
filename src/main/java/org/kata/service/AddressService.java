package org.kata.service;

import org.kata.dto.AddressDto;
import org.kata.dto.update.AddressUpdateDto;

public interface AddressService {
    AddressDto getActualAddress(String icp);

    AddressDto updateAddress(AddressUpdateDto dto);

}
