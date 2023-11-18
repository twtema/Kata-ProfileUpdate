package org.kata.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.kata.config.UrlProperties;
import org.kata.dto.WalletDto;
import org.kata.dto.enums.OperationStatus;
import org.kata.dto.transfer.CreditTransferDto;
import org.kata.service.CreditTransferService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Service
@Slf4j
public class CreditTransferServiceImpl implements CreditTransferService {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final UrlProperties urlProperties;

    private final WebClient loaderWebClient;
    @Value("${kafka.topic.credit-transfer-succeed}")
    private String kafkaTopic;

    public CreditTransferServiceImpl(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate, UrlProperties urlProperties) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
        this.urlProperties = urlProperties;
        this.loaderWebClient = WebClient.create(urlProperties.getProfileLoaderBaseUrl());
    }
    @Transactional
    @KafkaListener(topics = "creditTransferCreated", groupId = "profile-update")
    public void transfer(String message) {
        CreditTransferDto creditTransfer = null;

        try {
            creditTransfer = objectMapper.readValue(message, CreditTransferDto.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        log.info("Started money transfer from {} to {}", creditTransfer.getWalletIdFrom(), creditTransfer.getWalletIdTo());

        WalletDto from = updateBalance(creditTransfer.getWalletIdFrom(), creditTransfer.getBalanceFrom().subtract(creditTransfer.getAmount()));
        WalletDto to = updateBalance(creditTransfer.getWalletIdTo(), creditTransfer.getBalanceTo().add(creditTransfer.getAmount()));

        creditTransfer.setBalanceFrom(from.getBalance());
        creditTransfer.setBalanceTo(to.getBalance());

        publish(kafkaTopic, CreditTransferDto.builder()
                .transferUuid(creditTransfer.getTransferUuid())
                .walletIdFrom(from.getWalletId())
                .walletIdTo(to.getWalletId())
                .amount(creditTransfer.getAmount())
                .balanceFrom(from.getBalance())
                .balanceTo(to.getBalance())
                .currency(creditTransfer.getCurrency())
                .mobileFrom(creditTransfer.getMobileFrom())
                .mobileTo(creditTransfer.getMobileTo())
                .status(OperationStatus.SUCCEED)
                .build()
        );
    }
    private WalletDto updateBalance(String walletId, BigDecimal balance) {
        return loaderWebClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path(urlProperties.getProfileLoaderPatchWallet())
                        .queryParam("walletId", walletId)
                        .queryParam("balance", balance)
                        .build())
                .retrieve()
                .bodyToMono(WalletDto.class)
                .block();
    }

    private void publish(String topic, CreditTransferDto data)  {
        try {
            kafkaTemplate.send(topic, objectMapper.writeValueAsString(data));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
