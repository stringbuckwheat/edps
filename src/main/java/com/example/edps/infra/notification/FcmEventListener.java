package com.example.edps.infra.notification;

import com.example.edps.domain.notification.port.PaymentNotificationSender;
import com.example.edps.domain.payment.event.PaymentSuccessEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class FcmEventListener {

    private final PaymentNotificationSender paymentNotificationSender;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPaymentSuccess(PaymentSuccessEvent event) {
        paymentNotificationSender.sendPaymentSuccess(event.userId(), event.orderId(), event.amount());
    }
}
