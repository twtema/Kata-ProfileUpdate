package org.kata.controller;

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
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping("/update")
    public ResponseEntity<List<DocumentDto>> getDocument(@RequestBody DocumentUpdateDto dto) {
        return new ResponseEntity<>(documentService.updateDocuments(dto), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DocumentsNotFoundException.class)
    public ErrorMessage getDocumentHandler(DocumentsNotFoundException e) {
        return new ErrorMessage(e.getMessage());
    }
}
