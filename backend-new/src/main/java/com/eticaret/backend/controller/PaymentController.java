package com.eticaret.backend.controller;

import com.eticaret.backend.model.Product;
import com.eticaret.backend.repository.ProductRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;  // ✅ bunu ekle
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final ProductRepository productRepository;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    public PaymentController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PreAuthorize("hasRole('USER')")  // ✅ sadece USER olan kişiler bu endpoint'i kullanabilir
    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody List<CartItem> items) throws StripeException {
        if (items == null || items.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Sepet boş olamaz."));
        }
        Stripe.apiKey = stripeSecretKey;

        List<SessionCreateParams.LineItem> lineItems = items.stream().map(item -> {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Ürün bulunamadı: " + item.getProductId()));

            return SessionCreateParams.LineItem.builder()
                    .setQuantity((long) item.getQuantity())
                    .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("try")
                                    .setUnitAmount((long) (product.getPrice() * 100)) // kuruş cinsinden
                                    .setProductData(
                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                    .setName(product.getName())
                                                    .build())
                                    .build()
                    ).build();
        }).collect(Collectors.toList());

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:4200/success")
                .setCancelUrl("http://localhost:4200/cancel")
                .addAllLineItem(lineItems)
                .build();

        Session session = Session.create(params);

        Map<String, String> response = new HashMap<>();
        response.put("sessionId", session.getId());
        return ResponseEntity.ok(response);
    }

    // DTO sınıfı
    public static class CartItem {
        private Long productId;
        private int quantity;

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}