package com.eticaret.backend.service;

import com.eticaret.backend.dto.request.OrderRequest;
import com.eticaret.backend.dto.response.OrderItemResponse;
import com.eticaret.backend.dto.response.OrderResponse;
import com.eticaret.backend.model.KargoDurumu;
import com.eticaret.backend.model.Order;
import com.eticaret.backend.model.OrderItem;
import com.eticaret.backend.model.Product;
import com.eticaret.backend.model.OrderStatus;
import com.eticaret.backend.repository.OrderRepository;
import com.eticaret.backend.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0;

        for (var itemRequest : orderRequest.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            int quantity = itemRequest.getQuantity();
            double itemTotal = product.getPrice() * quantity;

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setTotalPrice(itemTotal);

            orderItems.add(orderItem);
            totalPrice += itemTotal;
        }

        Order order = new Order();
        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);

        for (OrderItem item : orderItems) {
            item.setOrder(order);
        }

        Order savedOrder = orderRepository.save(order);

        return convertToOrderResponse(savedOrder);
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> responseList = new ArrayList<>();

        for (Order order : orders) {
            responseList.add(convertToOrderResponse(order));
        }

        return responseList;
    }

    private OrderResponse convertToOrderResponse(Order order) {
        List<OrderItemResponse> itemResponses = new ArrayList<>();

        for (OrderItem item : order.getItems()) {
            itemResponses.add(new OrderItemResponse(
                    item.getProduct().getName(),
                    item.getProduct().getPrice(),
                    item.getQuantity(),
                    item.getTotalPrice()
            ));
        }

        return new OrderResponse(order.getId(), order.getTotalPrice(), itemResponses);
    }

    public Order updateKargoDurumu(Long orderId, KargoDurumu durum) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı"));
        order.setKargoDurumu(durum);
        return orderRepository.save(order);
    }

    public Order iadeEt(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı"));

        if (order.getKargoDurumu() != KargoDurumu.TESLIM_EDILDI) {
            throw new IllegalStateException("Yalnızca teslim edilen siparişler iade edilebilir.");
        }

        order.setStatus(OrderStatus.CANCELLED);
        order.setKargoDurumu(KargoDurumu.IPTAL_EDILDI);
        return orderRepository.save(order);
    }
}