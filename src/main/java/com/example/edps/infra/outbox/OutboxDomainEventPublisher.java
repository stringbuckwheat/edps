package com.example.edps.infra.outbox;

import com.example.edps.domain.common.port.DomainEventPublisher;
import com.example.edps.infra.kafka.message.EventEnvelope;
import com.example.edps.infra.outbox.service.OutboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxDomainEventPublisher implements DomainEventPublisher {

    private final OutboxService outboxService;

    @Override
    public <T> void publish(String topic, String key, T payload, String traceId) {
        outboxService.save(topic, key, EventEnvelope.of(traceId, topic, payload));
    }
}
