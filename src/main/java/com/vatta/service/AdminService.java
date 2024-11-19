package com.vatta.service;

import com.vatta.dto.ProductDTO;
import com.vatta.model.Product;
import com.vatta.model.Category;
import com.vatta.repository.ProductRepository;
import com.vatta.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public AdminService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // Obtener todos los productos
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Obtener un producto por ID y devolverlo como ProductDTO
    public ProductDTO getProductDTOById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return new ProductDTO(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImageUrl(),
                product.getCategory().getId()
        );
    }

    // Crear un nuevo producto a partir de un ProductDTO
    public void createProduct(ProductDTO productDTO) {
        Optional<Category> categoryOptional = categoryRepository.findById(productDTO.getCategoryId());
        if (!categoryOptional.isPresent()) {
            throw new RuntimeException("Category not found");
        }
        Category category = categoryOptional.get();

        Product product = new Product(
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getImageUrl(),
                category
        );
        productRepository.save(product);
    }

    // Actualizar un producto utilizando su ID y un ProductDTO
    public void updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<Category> categoryOptional = categoryRepository.findById(productDTO.getCategoryId());
        if (!categoryOptional.isPresent()) {
            throw new RuntimeException("Category not found");
        }
        Category category = categoryOptional.get();

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImageUrl(productDTO.getImageUrl());
        product.setCategory(category);

        productRepository.save(product);
    }

    // Eliminar un producto por ID
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }
}
