package com.eticaret.backend.controller;

import com.eticaret.backend.dto.response.DashboardStatsResponse;
import com.eticaret.backend.dto.response.OrderAdminResponse;
import com.eticaret.backend.model.*;
import com.eticaret.backend.repository.OrderRepository;
import com.eticaret.backend.repository.ProductRepository;
import com.eticaret.backend.repository.UserRepository;
import com.eticaret.backend.service.OrderService;
import com.eticaret.backend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final ProductRepository productRepository;
    private final ProductService productService;

    public AdminController(UserRepository userRepository, OrderRepository orderRepository,OrderService orderService,ProductRepository productRepository, ProductService productService) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // 🔻 Ürün ekle
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    // 🔻 Ürün sil
    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Ürün silindi.");
    }

    // 🔻 Ürün güncelle
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return ResponseEntity.ok(productService.updateProduct(id, updatedProduct));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("Kullanıcı silindi.");
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<String> updateUserRole(@PathVariable Long id, @RequestBody String newRole) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        user.setRole(newRole.replace("\"", "")); // eğer JSON'dan "..." şeklinde geliyorsa temizler
        userRepository.save(user);
        return ResponseEntity.ok("Kullanıcı rolü güncellendi.");
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long id, @RequestBody String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı"));

        order.setStatus(OrderStatus.valueOf(status.replace("\"", ""))); // "SHIPPED" gibi gelen stringi enum'a çevir

        orderRepository.save(order);
        return ResponseEntity.ok("Sipariş durumu güncellendi.");
    }

    @GetMapping("/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderAdminResponse>> getAllOrders() {
        List<OrderAdminResponse> response = orderRepository.findAll().stream()
                .map(OrderAdminResponse::new)
                .toList();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/dashboard-stats")
    public ResponseEntity<
            DashboardStatsResponse> getDashboardStats() {
        int totalOrders = orderRepository.findAll().size();
        int totalUsers = userRepository.findAll().size();
        int totalProducts = productRepository.findAll().size();

        double totalRevenue = orderRepository.findAll().stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();

        DashboardStatsResponse response = new DashboardStatsResponse(totalOrders, totalUsers, totalProducts, totalRevenue);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/orders/{id}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı."));

        if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.SHIPPED) {
            return ResponseEntity.badRequest().body("Kargo başlamış sipariş iptal edilemez.");
        }

        order.setStatus(OrderStatus.CANCELLED);
        order.setKargoDurumu(KargoDurumu.IPTAL_EDILDI);
        orderRepository.save(order);

        return ResponseEntity.ok("Sipariş admin tarafından iptal edildi ve ödeme iadesi başlatıldı.");
    }
}