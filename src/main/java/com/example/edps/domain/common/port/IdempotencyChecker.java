package com.example.edps.domain.common.port;

public interface IdempotencyChecker {
    boolean isProcessed(String eventId);
    void markProcessed(String eventId);
}
