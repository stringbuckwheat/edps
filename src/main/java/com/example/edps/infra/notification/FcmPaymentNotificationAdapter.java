package com.example.edps.infra.notification;

import com.example.edps.domain.notification.port.PaymentNotificationSender;
import com.example.edps.infra.fcm.UserDeviceTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class FcmPaymentNotificationAdapter implements PaymentNotificationSender {

    private final UserDeviceTokenRepository userDeviceTokenRepository;
    private final FcmNotifier fcmNotifier;

    @Override
    public void sendPaymentSuccess(String userId, Long orderId, int amount) {
        userDeviceTokenRepository.findByUserId(userId).ifPresentOrElse(
                token -> fcmNotifier.sendPaymentSuccess(token.getFcmToken(), orderId, amount),
                () -> log.debug("[FCM] 등록된 토큰 없음 userId={}", userId)
        );
    }
}
