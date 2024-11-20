package com.vatta.dto;

import com.vatta.model.Product;

public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private double price;
    private Long categoryId;
    private String imageUrl;  // Usamos imageUrl para la imagen del producto

    // Constructor vacío (opcional)
    public ProductDTO() {}

    // Constructor con parámetros
    public ProductDTO(Long id, String name, String description, double price, String imageUrl, Long categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
    }

    // Constructor para convertir un producto en DTO
    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.categoryId = product.getCategory() != null ? product.getCategory().getId() : null; // Verificación en caso de que category sea null
        this.imageUrl = product.getImageUrl();  // Aquí usamos la URL de la imagen del producto
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
