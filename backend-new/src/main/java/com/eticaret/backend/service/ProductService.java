package com.eticaret.backend.service;

import com.eticaret.backend.model.Product;
import com.eticaret.backend.repository.OrderItemRepository;
import com.eticaret.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    public ProductService(ProductRepository productRepository, OrderItemRepository orderItemRepository) {
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getByGender(String gender) {
        return productRepository.findByGender(gender);
    }

    public List<Product> getByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı."));

        boolean hasOrders = orderItemRepository.existsByProduct_Id(id);

        // Eğer ürün daha önce sipariş edilmişse ama şu an stoku 0 ise silinebilir
        if (hasOrders && product.getStock() > 0) {
            throw new IllegalStateException("Bu ürün sipariş edildiği için ve stokta bulunduğu için silinemez.");
        }

        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı."));

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setImageUrl(updatedProduct.getImageUrl());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setGender(updatedProduct.getGender());

        return productRepository.save(existingProduct);
    }
}