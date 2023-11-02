package org.kata.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.kata.dto.DocumentDto;
import org.kata.dto.update.DocumentUpdateDto;
import org.kata.exception.DocumentsNotFoundException;
import org.kata.service.DocumentService;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/document")
@Tag(name ="Documents Controller", description = "Document Media API")
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping("/update")
    @Operation(summary = "Update document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Not found - The document was not found"),
    })

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DocumentsNotFoundException.class)
    public ErrorMessage getDocumentHandler(DocumentsNotFoundException e) {
        return new ErrorMessage(e.getMessage());
    }
}
