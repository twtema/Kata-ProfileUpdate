package org.kata.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name ="Addresses Controller", description = "Addresses API")
public class AddressController {
    private final AddressService addressService;

    @PostMapping("/update")
    @Operation(summary = "Update address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Not found - The addresses was not found"),
    })
    public ResponseEntity<AddressDto> postAddress(@RequestBody AddressUpdateDto dto) {
        return new ResponseEntity<>(addressService.updateAddress(dto), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AddressNotFoundException.class)
    public ErrorMessage getAddressHandler(AddressNotFoundException e) {
        return new ErrorMessage(e.getMessage());
    }
}
