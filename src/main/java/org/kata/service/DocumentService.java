package org.kata.service;

import org.kata.dto.DocumentDto;
import org.kata.dto.recognite.RecognizeDocumentDto;
import org.kata.dto.update.DocumentUpdateDto;

import java.util.List;

public interface DocumentService {
    List<DocumentDto> getActualDocuments(String icp, String conversationId);

    List<DocumentDto> updateDocuments(DocumentUpdateDto dto, String conversationId);

    List<DocumentDto> updateOrCreateDocument(RecognizeDocumentDto dto, String conversationId);
}
