package org.kata.controller;

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
public class RecognizeDocument {

    private final DocumentService documentService;

    @PostMapping()
    public ResponseEntity<DocumentDto> postDocument(@RequestParam String icp, @RequestBody RecognizeDocumentDto dto) {
        if (!icp.equals(dto.getIcp())) {
            throw new DocumentsUpdateException("Документ не принадлжеит пользователю");
        }
        return new ResponseEntity<>(documentService.updateOrCreateDocument(dto).get(0), HttpStatus.OK);
    }

}
