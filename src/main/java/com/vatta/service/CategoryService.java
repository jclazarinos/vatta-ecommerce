package com.vatta.service;

import com.vatta.model.Category;
import com.vatta.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Método para obtener todas las categorías
    public List<Category> getAllCategories() {
        return categoryRepository.findAll(); // Obtiene todas las categorías de la base de datos
    }
}
