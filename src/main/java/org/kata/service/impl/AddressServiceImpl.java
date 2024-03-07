package org.kata.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.kata.config.UrlProperties;
import org.kata.dto.AddressDto;
import org.kata.dto.DocumentDto;
import org.kata.dto.IndividualDto;
import org.kata.dto.update.AddressUpdateDto;
import org.kata.exception.AddressNotFoundException;
import org.kata.exception.DocumentsNotFoundException;
import org.kata.service.AddressService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {
    private final UrlProperties urlProperties;
    private final WebClient loaderWebClient;

    public AddressServiceImpl(UrlProperties urlProperties) {
        this.urlProperties = urlProperties;
        this.loaderWebClient = WebClient.create(urlProperties.getProfileLoaderBaseUrl());
    }

    public AddressDto getActualAddress(String icp, String conversationId) {
        return loaderWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(urlProperties.getProfileLoaderGetAddress())
                        .queryParam("icp", icp)
                        .build())
                .header("conversationId", conversationId)
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        Mono.error(new AddressNotFoundException(
                                "Address with icp " + icp + " not found")
                        )
                )
                .bodyToMono(AddressDto.class)
                .block();
    }

    public AddressDto updateAddress(AddressUpdateDto dto, String conversationId) {
        loaderWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(urlProperties.getProfileLoaderPostAddress())
                        .queryParam("icp", dto.getIcp())
                        .build())
                .header("conversationId", conversationId)
                .body(Mono.just(dto), IndividualDto.class)
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        Mono.error(new DocumentsNotFoundException(
                                "Documents with icp " + dto.getIcp() + " not update")
                        )
                )
                .bodyToMono(new ParameterizedTypeReference<List<DocumentDto>>() {
                })
                .block();
        return getActualAddress(dto.getIcp(), conversationId);
    }
}
