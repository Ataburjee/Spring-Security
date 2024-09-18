package com.spring.ecom_backend.repository;

import com.spring.ecom_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p from Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', ':keyword', '%')) " +
            "OR LOWER(p.brand) LIKE LOWER(CONCAT('%', ':keyword', '%')) " +
            "OR LOWER(p.category) LIKE LOWER(CONCAT('%', ':keyword', '%'))")
    List<Product> searchProducts(String keyward);
}
