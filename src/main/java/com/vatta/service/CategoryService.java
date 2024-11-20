package com.vatta.service;

import com.vatta.model.Category;
import com.vatta.repository.CategoryRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Método para obtener todas las categorías
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Método para guardar una nueva categoría
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }
}
