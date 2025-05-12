package com.eticaret.backend.controller;

import com.eticaret.backend.dto.request.OrderItemRequest;
import com.eticaret.backend.dto.request.OrderRequest;
import com.eticaret.backend.model.*;
import com.eticaret.backend.repository.*;
import com.eticaret.backend.security.JwtService;
import com.eticaret.backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final JwtService jwtService;


    public OrderController(OrderRepository orderRepository,
                           ProductRepository productRepository,
                           UserRepository userRepository,
                           OrderService orderService,
                           CartItemRepository cartItemRepository,

                           OrderItemRepository orderItemRepository,
                           JwtService jwtService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.cartItemRepository = cartItemRepository;
        this.orderItemRepository = orderItemRepository;
        this.jwtService = jwtService;

    }

    /*@PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        Order order = new Order();
        order.setUser(user);

        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0;

        for (OrderItemRequest itemRequest : orderRequest.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setTotalPrice(product.getPrice() * item.getQuantity());
            item.setOrder(order);

            totalPrice += item.getTotalPrice();
            orderItems.add(item);
        }

        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.ok(savedOrder);
    }*/

    @GetMapping("/my-orders")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Order>> getUserOrders(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));
        List<Order> orders = orderRepository.findByUser(user);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        if (!user.getRole().equals("ROLE_ADMIN")) {
            return ResponseEntity.status(403).build(); // Yetkisiz
        }

        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}/kargo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> updateKargo(@PathVariable Long id, @RequestBody String durum) {
        KargoDurumu yeniDurum = KargoDurumu.valueOf(durum.toUpperCase());
        return ResponseEntity.ok(orderService.updateKargoDurumu(id, yeniDurum));
    }


    @PutMapping("/{id}/iade")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Order> iadeEt(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.iadeEt(id));
    }



    @PostMapping("/confirm")
    public ResponseEntity<String> confirmOrder(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ConfirmRequest request) {


        String token = authHeader.replace("Bearer ", "");
        String email = jwtService.extractEmail(token);

        System.out.println("✅ /confirm endpoint çalıştı");
        System.out.println("Token: " + token);
        System.out.println("Email: " + email);
        System.out.println("Adres: " + request.address);
        System.out.println("Ürün sayısı: " + request.items.size());

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return ResponseEntity.status(404).body("Kullanıcı bulunamadı");
        User user = userOpt.get();

        if (request.items == null || request.items.isEmpty()) {
            return ResponseEntity.badRequest().body("Sepetiniz boş.");
        }

        Order order = new Order();
        order.setUser(user);
        order.setAddress(request.address);
        order.setCreatedAt(LocalDateTime.now());
        order.setKargoDurumu(KargoDurumu.HAZIRLANIYOR);
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0;
        for (CartItemDTO cartItem : request.items) {
            Product product = productRepository.findById(cartItem.productId)
                    .orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));

            if (product.getStock() < cartItem.quantity) {
                return ResponseEntity.badRequest().body("Yetersiz stok: " + product.getName());
            }

            product.setStock(product.getStock() - cartItem.quantity);
            productRepository.save(product);

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(cartItem.quantity);
            item.setTotalPrice(product.getPrice() * cartItem.quantity); // bu satır önemli
            totalPrice += item.getTotalPrice(); // totalPrice hesaplanıyor
            orderItems.add(item);
        }

        order.setItems(orderItems);
        order.setTotalPrice(totalPrice); // BU SATIRI EKLE
        Order savedOrder = orderRepository.save(order); // 3. Siparişi kaydet
        orderItemRepository.saveAll(orderItems);
        return ResponseEntity.ok("Sipariş başarıyla tamamlandı.");
    }

    // DTO sınıfları
    public static class ConfirmRequest {
        public String address;
        public List<CartItemDTO> items;
    }
    public static class CartItemDTO {
        public Long productId;
        public int quantity;
    }
}