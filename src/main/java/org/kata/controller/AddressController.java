package org.kata.controller;

import lombok.RequiredArgsConstructor;
import org.kata.dto.AddressDto;
import org.kata.dto.update.AddressUpdateDto;
import org.kata.exception.AddressNotFoundException;
import org.kata.service.AddressService;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/address")
public class AddressController {
    private final AddressService addressService;

    @PostMapping("/update")
    public ResponseEntity<AddressDto> getAddress(@RequestBody AddressUpdateDto dto) {
        return new ResponseEntity<>(addressService.updateAddress(dto), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AddressNotFoundException.class)
    public ErrorMessage getAddressHandler(AddressNotFoundException e) {
        return new ErrorMessage(e.getMessage());
    }
}
