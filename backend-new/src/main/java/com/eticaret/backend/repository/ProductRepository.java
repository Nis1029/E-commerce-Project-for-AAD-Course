package com.eticaret.backend.repository;

import com.eticaret.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByGender(String gender);
    List<Product> findByCategory(String category);
}
