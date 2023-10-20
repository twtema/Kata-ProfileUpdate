package org.kata.controller;

import lombok.RequiredArgsConstructor;
import org.kata.dto.IndividualDto;
import org.kata.dto.update.IndividualUpdateDto;
import org.kata.exception.IndividualNotFoundException;
import org.kata.service.IndividualService;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("v1/individual")
public class IndividualController {

    private final IndividualService individualService;

    @PostMapping("/update")
    public ResponseEntity<IndividualDto> getIndividual(@RequestBody IndividualUpdateDto dto) {
        return new ResponseEntity<>(individualService.updateIndividual(dto), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IndividualNotFoundException.class)
    public ErrorMessage getIndividualHandler(IndividualNotFoundException e) {
        return new ErrorMessage(e.getMessage());
    }

}
