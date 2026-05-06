package com.example.edps.domain.notification.port;

public interface AlertSender {
    void sendDlqAlert(String originalTopic, String eventId, Long orderId, Long paymentId, String cause, String message);
}
