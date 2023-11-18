package org.kata.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.kata.config.UrlProperties;
import org.kata.dto.WalletDto;
import org.kata.dto.enums.CreditTransferType;
import org.kata.dto.enums.CurrencyType;
import org.kata.dto.enums.OperationStatus;
import org.kata.dto.transfer.CreditTransferDto;
import org.kata.dto.transfer.CreditTransferRequest;
import org.kata.exception.credit_transfer.InsufficientCreditException;
import org.kata.exception.credit_transfer.InvalidTransferAmountException;
import org.kata.exception.WalletNotFoundException;
import org.kata.service.CreditTransferValidatorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Slf4j
public class CreditTransferValidatorServiceImpl implements CreditTransferValidatorService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final WebClient loaderWebClient;

    private final UrlProperties urlProperties;

    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.credit-transfer-created}")
    private String kafkaTopic;

    public CreditTransferValidatorServiceImpl(KafkaTemplate<String, String> kafkaTemplate, UrlProperties urlProperties, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.loaderWebClient = WebClient.create(urlProperties.getProfileLoaderBaseUrl());
        this.urlProperties = urlProperties;
        this.objectMapper = objectMapper;
    }


    private CreditTransferType defineTransferType() {
        //TODO определить тип по регулярке
        return CreditTransferType.PHONE;
    }

    private void checkAmount(CreditTransferRequest data) {
        if (data.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransferAmountException("Transfer amount can't be zero or negative");
        }
    }
    public void process(CreditTransferRequest data) {
        checkAmount(data);

        if (defineTransferType().compareTo(CreditTransferType.PHONE) == 0) {
            log.info("Money transfer request by mobile from {} to {}", data.getFrom(), data.getTo());

            WalletDto from = getWalletByMobileAndCurrency(data.getFrom(), data.getCurrencyType());
            WalletDto to = getWalletByMobileAndCurrency(data.getTo(), data.getCurrencyType());

            if (from.getBalance().compareTo(data.getAmount()) < 0) {
                throw new InsufficientCreditException(
                        "Insufficient credit for wallet "
                                + data.getCurrencyType());
            }
            log.info("Pending money transfer from {} to {}", from.getWalletId(), to.getWalletId());
            publish(kafkaTopic, CreditTransferDto.builder()
                    .transferUuid(UUID.randomUUID().toString())
                    .amount(data.getAmount())
                    .walletIdFrom(from.getWalletId())
                    .walletIdTo(to.getWalletId())
                    .balanceFrom(from.getBalance())
                    .balanceTo(to.getBalance())
                    .mobileFrom(data.getFrom())
                    .mobileTo(data.getTo())
                    .currency(data.getCurrencyType())
                    .status(OperationStatus.VERIFIED)
                    .build());

        }
    }

    private WalletDto getWalletByMobileAndCurrency(String mobile, CurrencyType currencyType) {
        return loaderWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(urlProperties.getProfileLoaderGetWalletByMobileAndCurrency())
                        .queryParam("mobile", mobile)
                        .queryParam("currency", currencyType)
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError,
                        response -> response.bodyToMono(Exception.class)
                                .flatMap(e -> Mono.error(
                                        new WalletNotFoundException(e.getMessage()
                                        ))))
                .bodyToMono(WalletDto.class)
                .block();
    }

    private void publish(String kafkaTopic, CreditTransferDto data) {
        try {
            kafkaTemplate.send(kafkaTopic, objectMapper.writeValueAsString(data));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
