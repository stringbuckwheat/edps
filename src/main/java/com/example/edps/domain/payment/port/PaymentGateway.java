package com.example.edps.domain.payment.port;

import com.example.edps.domain.payment.event.PaymentRequestedCommand;

public interface PaymentGateway {
    PaymentGatewayResult requestPayment(PaymentRequestedCommand cmd);
}
