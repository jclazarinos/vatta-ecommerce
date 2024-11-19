package com.vatta.service;

import com.vatta.model.Cart;
import com.vatta.model.CartItem;
import com.vatta.model.Product;
import com.vatta.repository.CartRepository;
import com.vatta.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    // Obtener el carrito de un usuario
    public Cart getCart(Long userId) {
        return cartRepository.findById(userId).orElse(new Cart());
    }

    // Agregar un producto al carrito
    public void addProductToCart(Long userId, Long productId, Integer quantity) {
        Cart cart = getCart(userId);
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            CartItem cartItem = new CartItem(product, quantity);
            cart.addItem(cartItem);
            cartRepository.save(cart);
        }
    }

    // Eliminar un producto del carrito
    public void removeProductFromCart(Long userId, Long cartItemId) {
        Cart cart = getCart(userId);
        cart.getItems().removeIf(item -> item.getId().equals(cartItemId));
        cartRepository.save(cart);
    }
}
