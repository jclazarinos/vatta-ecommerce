package com.vatta.controller;

import com.vatta.dto.ProductDTO;
import com.vatta.service.AdminService;
import com.vatta.service.CategoryService;

import jakarta.servlet.http.HttpSession;

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

    // Vista del DashbAoard
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        // Verificar si el usuario está autenticado y es admin
        if (session.getAttribute("username") == null || 
            !"ROLE_ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }
        return "admin/products/dashboard";
    }

    // Lista de productos
    @GetMapping("/products")
    public String listProducts(HttpSession session, Model model) {
        // Verificar si el usuario está autenticado y es admin
        if (session.getAttribute("username") == null || 
            !"ROLE_ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }
        model.addAttribute("products", adminService.getAllProducts());
        return "admin/products/list";
    }

    // Crear un nuevo producto
    @GetMapping("/products/create")
    public String showCreateProductForm(HttpSession session, Model model) {
        // Verificar si el usuario está autenticado y es admin
        if (session.getAttribute("username") == null || 
            !"ROLE_ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }
        model.addAttribute("productDTO", new ProductDTO()); // Para crear un nuevo producto
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/products/create";
    }

    // Guardar un nuevo producto
    @PostMapping("/products")
    public String createProduct(HttpSession session, @ModelAttribute("productDTO") ProductDTO productDTO) {
        // Verificar si el usuario está autenticado y es admin
        if (session.getAttribute("username") == null || 
            !"ROLE_ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }
        // Aquí se crea el producto usando el servicio
        adminService.createProduct(productDTO);
        return "redirect:/admin/products"; // Redirige a la lista de productos
    }

    // Editar un producto
    @GetMapping("/products/edit/{id}")
    public String showEditProductForm(HttpSession session, @PathVariable Long id, Model model) {
        // Verificar si el usuario está autenticado y es admin
        if (session.getAttribute("username") == null || 
            !"ROLE_ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }
        ProductDTO productDTO = adminService.getProductDTOById(id);
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/products/edit";
    }

    // Actualizar un producto
    @PostMapping("/products/edit/{id}")
    public String updateProduct(HttpSession session, @PathVariable Long id, @ModelAttribute("productDTO") ProductDTO productDTO) {
        // Verificar si el usuario está autenticado y es admin
        if (session.getAttribute("username") == null || 
            !"ROLE_ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }
        adminService.updateProduct(id, productDTO);
        return "redirect:/admin/products";
    }

    // Eliminar un producto
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(HttpSession session, @PathVariable Long id) {
        // Verificar si el usuario está autenticado y es admin
        if (session.getAttribute("username") == null || 
            !"ROLE_ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }
        adminService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    // Otros métodos según sea necesario
}
