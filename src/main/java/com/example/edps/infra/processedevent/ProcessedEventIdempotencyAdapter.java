package com.example.edps.infra.processedevent;

import com.example.edps.domain.common.port.IdempotencyChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessedEventIdempotencyAdapter implements IdempotencyChecker {

    private final ProcessedEventRepository processedEventRepository;

    @Override
    public boolean isProcessed(String eventId) {
        return processedEventRepository.existsByEventId(eventId);
    }

    @Override
    public void markProcessed(String eventId) {
        processedEventRepository.save(new ProcessedEvent(eventId));
    }
}
