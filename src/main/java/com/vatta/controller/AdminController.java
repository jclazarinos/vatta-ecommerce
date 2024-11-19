package com.vatta.controller;

import com.vatta.dto.ProductDTO;
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

    // Vista del Dashboard
    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    // Lista de productos
    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", adminService.getAllProducts());
        return "admin/products/list";
    }

    // Crear un nuevo producto
    @GetMapping("/products/create")
    public String showCreateProductForm(Model model) {
        model.addAttribute("productDTO", new ProductDTO()); // Para crear un nuevo producto
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/products/create";
    }

    // Guardar un nuevo producto
    @PostMapping("/products")
    public String createProduct(@ModelAttribute("productDTO") ProductDTO productDTO) {
        // Aquí se crea el producto usando el servicio
        adminService.createProduct(productDTO);
        return "redirect:/admin/products"; // Redirige a la lista de productos
    }

    // Editar un producto
    @GetMapping("/products/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        ProductDTO productDTO = adminService.getProductDTOById(id);
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/products/edit";
    }

    // Actualizar un producto
    @PostMapping("/products/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute("productDTO") ProductDTO productDTO) {
        adminService.updateProduct(id, productDTO);
        return "redirect:/admin/products";
    }

    // Otros métodos según sea necesario
}
