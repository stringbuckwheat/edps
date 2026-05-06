package com.example.edps.domain.notification.port;

public interface PaymentNotificationSender {
    void sendPaymentSuccess(String userId, Long orderId, int amount);
}
