package com.vatta.repository;

import com.vatta.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // Aquí puedes añadir métodos personalizados si es necesario
}
