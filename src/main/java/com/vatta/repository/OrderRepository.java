package com.vatta.repository;

import com.vatta.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Aquí puedes añadir métodos personalizados si es necesario
}
