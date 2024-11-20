package com.vatta.controller;

import com.vatta.dto.ProductDTO;
import com.vatta.model.Category;
import com.vatta.service.AdminService;
import com.vatta.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final CategoryService categoryService;

    public AdminController(AdminService adminService, CategoryService categoryService) {
        this.adminService = adminService;
        this.categoryService = categoryService;
    }

    // Mostrar productos en el panel
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("products", adminService.getAllProducts());
        return "admin/dashboard";
    }

    // Crear un nuevo producto (Formulario)
    @GetMapping("/product-create")
    public String showCreateProductForm(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("productDTO", new ProductDTO());
        return "admin/product-create";
    }

    // Guardar un nuevo producto
    @PostMapping("/product-create")
    public String createProduct(@ModelAttribute ProductDTO productDTO) {
        adminService.createProduct(productDTO);
        return "redirect:/admin/dashboard";
    }

    // Crear una nueva categoría (Formulario)
    @GetMapping("/category-create")
    public String showCreateCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category-create";
    }

    // Guardar una nueva categoría
    @PostMapping("/category-create")
    public String createCategory(@ModelAttribute Category category) {
        categoryService.createCategory(category); // Método para guardar la nueva categoría
        return "redirect:/admin/dashboard"; // Redirigir al dashboard después de crear la categoría
    }

    // Editar un producto (Formulario)
    @GetMapping("/product-edit/{id}")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        ProductDTO productDTO = adminService.getProductDTOById(id);
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/product-edit";
    }

    // Actualizar un producto
    @PostMapping("/product-edit/{id}")
    public String updateProduct(@PathVariable("id") Long id, @ModelAttribute ProductDTO productDTO) {
        adminService.updateProduct(id, productDTO);
        return "redirect:/admin/dashboard";
    }

    // Eliminar un producto
    @GetMapping("/product-delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        adminService.deleteProduct(id);
        return "redirect:/admin/dashboard";
    }
}
