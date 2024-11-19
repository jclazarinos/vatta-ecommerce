package com.vatta.controller;

import com.vatta.model.Product;
import com.vatta.service.ProductService;  // Asegúrate de tener este servicio
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final ProductService productService;

    // Constructor para inyectar ProductService
    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String redirectToHome() {
        return "shop/home";
    }

    // Vista de la página de inicio
    @GetMapping("/home")
    public String home(Model model) {
        // Obtener productos destacados o los primeros 5 productos
        List<Product> featuredProducts = productService.getFeaturedProducts(); // Método que debes implementar en el servicio
        model.addAttribute("featuredProducts", featuredProducts);

        // Deberías agregar otros atributos al modelo, como categorías o promociones
        return "shop/home";  // Redirige a la plantilla 'home.html' (debes crear esta plantilla)
    }
}
