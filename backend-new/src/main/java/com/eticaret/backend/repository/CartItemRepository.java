package com.eticaret.backend.repository;

import com.eticaret.backend.model.CartItem;
import com.eticaret.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user); // 🔍 Kullanıcının sepetini getirir
    void deleteAllByUser(User user);      // 🧹 Sepeti temizler
}