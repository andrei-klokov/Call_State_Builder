package com.avaya.ccaas.call_state_builder.handler;

import com.avaya.ccaas.call_state_builder.redis.model.ParticipantContext;
import com.avaya.ccaas.call_state_builder.redis.repo.CallContextRepository;
import com.avaya.ccaas.participant_state_adapter.avro.ParticipantStateAvro;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddParticipantHandler implements EventHandler<ParticipantStateAvro>{

    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantStateAvro.class);
    private final CallContextRepository repository;

    @Override
    public void handle(final ParticipantStateAvro value) {
        LOGGER.info("ParticipantStateAvro " + value);
        String key = value.getCallId();
        ParticipantContext participantContext = ParticipantContext.createFromKafkaMessage(value);
        repository.addParticipant(key, participantContext);
        LOGGER.info("Participant {id=" + participantContext.getId() +
            "} has been added to the call context {id=" + key + "}");
    }
}
