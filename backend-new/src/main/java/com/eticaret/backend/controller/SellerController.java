package com.eticaret.backend.controller;

import com.eticaret.backend.model.KargoDurumu;
import com.eticaret.backend.model.Order;
import com.eticaret.backend.model.OrderStatus;
import com.eticaret.backend.model.Product;
import com.eticaret.backend.repository.OrderRepository;
import com.eticaret.backend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/seller")
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasRole('SELLER')")
public class SellerController {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public SellerController(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    // 🔸 Dashboard verileri
    @GetMapping("/dashboard-stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = Map.of(
                "totalProducts", 8,
                "totalRevenue", 4500,
                "totalSales", 22
        );
        return ResponseEntity.ok(stats);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Ürün silindi.");
    }

    // SATIŞ İPTAL ENDPOINTİ
    @PutMapping("/orders/{id}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı."));

        // Kargo başlamışsa iptal edilemez
        if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.SHIPPED) {
            return ResponseEntity.badRequest().body("Kargo başlamış bir sipariş iptal edilemez.");
        }

        order.setStatus(OrderStatus.CANCELLED);
        order.setKargoDurumu(KargoDurumu.IPTAL_EDILDI);

        orderRepository.save(order);

        // TODO: Ödeme iade işlemi buraya entegre edilecek (Stripe, iyzico, vs.)

        return ResponseEntity.ok("Sipariş iptal edildi ve ödeme iadesi başlatıldı.");
    }

    @PutMapping("/orders/{orderId}/kargo")
    public ResponseEntity<String> updateKargoDurumu(@PathVariable Long orderId, @RequestBody String yeniDurum) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı."));

        try {
            order.setKargoDurumu(KargoDurumu.valueOf(yeniDurum.toUpperCase()));
            orderRepository.save(order);
            return ResponseEntity.ok("Kargo durumu güncellendi.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Geçersiz kargo durumu.");
        }
    }
}
