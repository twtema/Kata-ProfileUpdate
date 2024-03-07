package org.kata.service;

import org.kata.dto.IndividualDto;
import org.kata.dto.update.IndividualUpdateDto;

public interface IndividualService {
    IndividualDto getIndividual(String icp, String conversationId);

    IndividualDto updateIndividual(IndividualUpdateDto dto, String conversationId);

}
