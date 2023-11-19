package org.kata.service.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.kata.config.UrlProperties;
import org.kata.dto.ContactMediumDto;
import org.kata.dto.notify.UpdateContactMessage;
import org.kata.dto.update.ContactMediumUpdateDto;
import org.kata.exception.ContactMediumNotFoundException;
import org.kata.service.ContactMediumService;
import org.kata.service.KafkaMessageSender;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class ContactMediumServiceImpl implements ContactMediumService {
    private final UrlProperties urlProperties;
    private final WebClient loaderWebClient;
    private final KafkaMessageSender kafkaMessageSender;

    public ContactMediumServiceImpl(UrlProperties urlProperties,
                                    KafkaMessageSender kafkaMessageSender) {
        this.urlProperties = urlProperties;
        this.loaderWebClient = WebClient.create(urlProperties.getProfileLoaderBaseUrl());
        this.kafkaMessageSender = kafkaMessageSender;
    }

    public List<ContactMediumDto> getActualContactMedium(String icp) {
        return loaderWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(urlProperties.getProfileLoaderGetContactMedium())
                        .queryParam("icp", icp)
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        Mono.error(new ContactMediumNotFoundException(
                                "ContactMedium with icp " + icp + " not found")
                        )
                )
                .bodyToMono(new ParameterizedTypeReference<List<ContactMediumDto>>() {
                })
                .block();
    }

    @SneakyThrows
    public List<ContactMediumDto> updateContact(ContactMediumUpdateDto dto) {
        List<ContactMediumDto> oldContact = getActualContactMedium(dto.getIcp()).stream()
                .filter(con -> con.getType().equals(dto.getType()))
                .toList();

        UpdateContactMessage updateContactMessage = new UpdateContactMessage();
        updateContactMessage.setIcp(dto.getIcp());
        updateContactMessage.setOldContactValue(oldContact.get(0).getValue());
        updateContactMessage.setNewContactValue(dto.getValue());

        loaderWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(urlProperties.getProfileLoaderPostContactMedium())
                        .queryParam("icp", dto.getIcp())
                        .build())
                .body(Mono.just(dto), ContactMediumDto.class)
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        Mono.error(new ContactMediumNotFoundException(
                                "ContactMedium with icp " + dto.getIcp() + " not update")
                        )
                )
                .bodyToMono(ContactMediumDto.class)
                .block();

        kafkaMessageSender.sendMessage(updateContactMessage);
        return getActualContactMedium(dto.getIcp());
    }

}
