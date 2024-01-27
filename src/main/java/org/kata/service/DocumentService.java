package org.kata.service;

import org.kata.dto.DocumentDto;
import org.kata.dto.recognite.RecognizeDocumentDto;
import org.kata.dto.update.DocumentUpdateDto;

import java.util.List;

public interface DocumentService {
    List<DocumentDto> getActualDocuments(String icp);

    DocumentDto updateDocuments(DocumentUpdateDto dto);

    List<DocumentDto> updateOrCreateDocument(RecognizeDocumentDto dto);
}
