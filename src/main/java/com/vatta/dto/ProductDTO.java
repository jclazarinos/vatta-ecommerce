package com.vatta.dto;

import com.vatta.model.Product;

public class ProductDTO {

    private String name;
    private String description;
    private double price;
    private Long categoryId; // Usamos categoryId como campo para la relación con Category

    // Constructor vacío (opcional)
    public ProductDTO() {}

    // Constructor con parámetros
    public ProductDTO(String name, String description, double price, String string, Long categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }

    // Constructor para convertir un producto en DTO
    public ProductDTO(Product product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.categoryId = product.getCategory().getId(); // Aquí usamos la categoría asociada
    }

    // Getters y setters
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
        throw new UnsupportedOperationException("Unimplemented method 'getImageUrl'");
    }

    public void setImageUrl(String imageUrl) {
        throw new UnsupportedOperationException("Unimplemented method 'setImageUrl'");
    }
}
