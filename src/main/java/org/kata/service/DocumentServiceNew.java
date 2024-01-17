package org.kata.service;

import org.kata.dto.DocumentDtoNew;
import org.kata.dto.recognite.RecognizeDocumentDtoNew;
import org.kata.dto.update.DocumentUpdateDtoNew;

import java.util.List;

public interface DocumentServiceNew {
    List<DocumentDtoNew> getActualDocumentsNew(String icp);

    List<DocumentDtoNew> updateDocumentsNew(DocumentUpdateDtoNew dto);

    List<DocumentDtoNew> updateOrCreateDocumentNew(RecognizeDocumentDtoNew dto);
}
