package org.kata.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.kata.config.UrlProperties;
import org.kata.dto.DocumentDto;
import org.kata.dto.DocumentDtoNew;
import org.kata.dto.recognite.RecognizeDocumentDtoNew;
import org.kata.dto.update.DocumentUpdateDtoNew;
import org.kata.exception.DocumentsNotFoundException;
import org.kata.service.DocumentServiceNew;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DocumentServiceImplNew implements DocumentServiceNew {
    private final UrlProperties urlProperties;
    private final WebClient loaderWebClient;

    public DocumentServiceImplNew(UrlProperties urlProperties) {
        this.urlProperties = urlProperties;
        this.loaderWebClient = WebClient.create(urlProperties.getProfileLoaderBaseUrl());
    }

    public List<DocumentDtoNew> getActualDocumentsNew(String icp) {
        return loaderWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(urlProperties.getProfileLoaderGetDocuments())
                        .queryParam("icp", icp)
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        Mono.error(new DocumentsNotFoundException(
                                "Documents with icp " + icp + " not found")
                        )
                )
                .bodyToMono(new ParameterizedTypeReference<List<DocumentDtoNew>>() {
                })
                .block();
    }

    public List<DocumentDtoNew> updateDocumentsNew(DocumentUpdateDtoNew dto) {

        loaderWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(urlProperties.getProfileLoaderPostDocuments())
                        .queryParam("icp", dto.getIcp())
                        .build())
                .body(Mono.just(dto), DocumentDto.class)
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        Mono.error(new DocumentsNotFoundException(
                                "Documents with icp " + dto.getIcp() + " not update")
                        )
                )
                .bodyToMono(new ParameterizedTypeReference<List<DocumentDto>>() {
                })
                .block();
        return getActualDocumentsNew(dto.getIcp());
    }

    public List<DocumentDtoNew> updateOrCreateDocumentNew(RecognizeDocumentDtoNew dto) {
        List<DocumentDtoNew> oldDocuments =
                getActualDocumentsNew(dto.getIcp()).stream()
                        .filter(doc -> doc.getDocumentType().equals(dto.getDocumentType())).toList();
        ;

        if (CollectionUtils.isEmpty(oldDocuments)) {
            return null;
        }

        List<DocumentDtoNew> newDocument = List.of(DocumentDtoNew.builder()
                .icp(dto.getIcp())
                .documentSerial(dto.getDocumentSerial())
                .documentNumber(dto.getDocumentNumber())
                .documentType(dto.getDocumentType())
                .issueDate(dto.getIssueDate())
                .expirationDate(dto.getExpirationDate())
//                .externalDate(dto.getExternalDate())
                .externalDate(Date.from(Instant.now()))
                .build());

        return loaderWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(urlProperties.getProfileLoaderPostDocuments())
                        .queryParam("icp", dto.getIcp())
                        .build())
                .body(Mono.just(newDocument), DocumentDto.class)
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        Mono.error(new DocumentsNotFoundException(
                                "Documents with icp " + dto.getIcp() + " not create")
                        )
                )
                .bodyToMono(new ParameterizedTypeReference<List<DocumentDtoNew>>() {
                })
                .block();
    }
}
