package com.example.edps.domain.payment.port;

public record PaymentGatewayResult(
        boolean success,
        String pgTxId,
        String reason
) {}
