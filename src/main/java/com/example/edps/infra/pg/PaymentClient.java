package com.example.edps.infra.pg;

import com.example.edps.domain.order.enums.PgScenario;
import com.example.edps.domain.payment.event.PaymentRequestedCommand;
import com.example.edps.domain.payment.port.PaymentGateway;
import com.example.edps.domain.payment.port.PaymentGatewayResult;
import com.example.edps.global.error.exception.PgBusinessException;
import com.example.edps.global.error.exception.PgTransientException;
import com.example.edps.infra.pg.dto.PgPaymentRequest;
import com.example.edps.infra.pg.dto.PgPaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@Slf4j
public class PaymentClient implements PaymentGateway {
    private final WebClient webClient;

    public PaymentClient(@Value("${pg.base-url}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public PaymentGatewayResult requestPayment(PaymentRequestedCommand cmd) {
        PgScenario scenario = cmd.scenario();
        PgPaymentResponse res = webClient.post()
                .uri("/pg/payments")
                .headers(h -> {
                    if (scenario != null) {
                        h.add("X-MOCK-SCENARIO", scenario.name());
                    }
                })
                .bodyValue(new PgPaymentRequest(cmd.paymentId(), cmd.total()))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r ->
                        r.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new PgBusinessException(r.statusCode().value(), body)))
                )
                .onStatus(HttpStatusCode::is5xxServerError, r ->
                        r.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new PgTransientException(r.statusCode().value(), body)))
                )
                .bodyToMono(PgPaymentResponse.class)
                .timeout(Duration.ofSeconds(2)) // 타임아웃 2초
                .doOnNext(body -> log.info("PG 응답: {}", body))
                .block();

        return new PaymentGatewayResult(res.isSuccess(), res.pgTxId(), res.reason());
    }
}
