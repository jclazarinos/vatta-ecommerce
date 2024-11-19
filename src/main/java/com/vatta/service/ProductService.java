package com.vatta.service;

import com.vatta.dto.ProductDTO;
import com.vatta.model.Product;
import com.vatta.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAll()
            .stream()
            .map(product -> new ProductDTO(product))
            .collect(Collectors.toList());
    }

    public ProductDTO findById(Long id) {
        return productRepository.findById(id)
            .map(product -> new ProductDTO(product))
            .orElse(null);
    }

    public List<Product> getFeaturedProducts() {
        return productRepository.findTop5ByOrderByIdDesc();  // Método de ejemplo para obtener los 5 productos más recientes
    }
}
