package org.kata.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.kata.dto.DocumentDto;
import org.kata.dto.recognite.RecognizeDocumentDto;
import org.kata.exception.DocumentsUpdateException;
import org.kata.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/postRecognitionDocument")
@Tag(name = "Recognize Documents")
public class RecognizeDocument {

    private final DocumentService documentService;

    @PostMapping()
    @Operation(summary = "Create or update document")
    public ResponseEntity<DocumentDto> postDocument(
            @RequestHeader(value = "conversationId", required = false) String conversationId,
            @RequestParam String icp,
            @RequestBody RecognizeDocumentDto dto
    ) {
        if (!icp.equals(dto.getIcp())) {
            throw new DocumentsUpdateException("Документ не принадлжеит пользователю");
        }
        return new ResponseEntity<>(documentService.updateOrCreateDocument(dto, conversationId).get(0), HttpStatus.OK);
    }
}
