package com.vatta.controller;

import com.vatta.dto.ProductDTO;
import com.vatta.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/shop")  // Cambiamos el base mapping a /shop
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"/products", "/products/"})  // Manejamos ambas variantes de la URL
    public String listProducts(Model model) {
        try {
            model.addAttribute("products", productService.findAll());
            return "shop/products";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar los productos: " + e.getMessage());
            return "shop/products"; // Retornamos la misma vista pero con mensaje de error
        }
    }

    @GetMapping("/products/{id}")
    public String productDetail(@PathVariable Long id, 
                              Model model, 
                              RedirectAttributes redirectAttributes) {
        try {
            ProductDTO productDTO = productService.findById(id);
            if (productDTO == null) {
                redirectAttributes.addFlashAttribute("error", "Producto no encontrado");
                return "redirect:/shop/products";
            }
            model.addAttribute("product", productDTO);
            return "shop/product-detail";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cargar el producto: " + e.getMessage());
            return "redirect:/shop/products";
        }
    }
}