package com.vatta.controller;

import com.vatta.model.Cart;
import com.vatta.service.CartService;
import com.vatta.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final OrderService orderService;

    public CartController(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @GetMapping
    public String viewCart(@RequestParam Long userId, Model model) {
        Cart cart = cartService.getCart(userId);
        model.addAttribute("cart", cart);
        return "cart/cart";
    }

    @PostMapping("/add")
    public String addProductToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam Integer quantity) {
        cartService.addProductToCart(userId, productId, quantity);
        return "redirect:/cart?userId=" + userId;
    }

    @PostMapping("/remove")
    public String removeProductFromCart(@RequestParam Long userId, @RequestParam Long cartItemId) {
        cartService.removeProductFromCart(userId, cartItemId);
        return "redirect:/cart?userId=" + userId;
    }

    @GetMapping("/checkout")
    public String checkout(@RequestParam Long userId, Model model) {
        Cart cart = cartService.getCart(userId);
        model.addAttribute("cart", cart);
        return "cart/checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(@RequestParam Long userId) {
        Cart cart = cartService.getCart(userId);
        orderService.createOrder(userId, cart);
        return "redirect:/order/confirmation";
    }
}
