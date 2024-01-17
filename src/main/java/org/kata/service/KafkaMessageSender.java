package org.kata.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kata.dto.notify.UpdateContactMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageSender {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.create}")
    private String kafkaTopic;


    public void sendMessage(UpdateContactMessage dto) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(dto);
        kafkaTemplate.send(kafkaTopic, message);
        log.info("Message send to topic:{}", kafkaTopic);
    }
}
