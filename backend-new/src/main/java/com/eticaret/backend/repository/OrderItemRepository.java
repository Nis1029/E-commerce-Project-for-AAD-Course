package com.eticaret.backend.repository;

import com.eticaret.backend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    boolean existsByProduct_Id(Long productId); // 🔥 Buradaki _ işareti JPA için önemli!
}