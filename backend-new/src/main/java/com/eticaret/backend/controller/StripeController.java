package com.eticaret.backend.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class StripeController {

    @PostMapping("/create-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentRequest paymentRequest) {
        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(paymentRequest.getAmount()) // kuruş cinsinden, örn: 100 TL = 10000
                    .setCurrency("try")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);
            return ResponseEntity.ok(intent.getClientSecret());

        } catch (StripeException e) {
            return ResponseEntity.status(500).body("Stripe hatası: " + e.getMessage());
        }
    }

    public static class PaymentRequest {
        private Long amount;

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }
    }
}