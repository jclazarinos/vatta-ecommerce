package com.vatta.repository;

import com.vatta.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContaining(String name);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByNameContainingAndCategoryId(String name, Long categoryId);

    List<Product> findTop5ByOrderByIdDesc();
}
