package com.example.edps.domain.common.port;

public interface DomainEventPublisher {
    <T> void publish(String topic, String key, T payload, String traceId);
}
