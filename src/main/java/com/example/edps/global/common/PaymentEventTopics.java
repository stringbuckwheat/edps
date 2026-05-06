package com.example.edps.global.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaymentEventTopics {
    public static final String PAYMENT_COMMAND_REQUESTED = "payment.command.requested";

    public static final String PAYMENT_EVENT_SUCCEEDED = "payment.event.succeeded";
    public static final String PAYMENT_EVENT_FAILED = "payment.event.failed";

    public static final String PAYMENT_COMMAND_REQUESTED_DLQ = "payment.command.requested.dlq";
    public static final String PAYMENT_RESULT_DLQ = "payment.result.dlq";
}
