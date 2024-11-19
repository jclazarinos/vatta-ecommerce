package com.vatta.service;

import com.vatta.model.Order;
import com.vatta.model.OrderDetail;
import com.vatta.model.Cart;
import com.vatta.model.CartItem;
import com.vatta.repository.OrderRepository;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Crear una nueva orden a partir de un carrito
    public void createOrder(Long userId, Cart cart) {
        Order order = new Order();
        order.setUser("User" + userId); // Esto debería ser dinámico basado en el usuario
        order.setTotalPrice(cart.getTotalPrice());

        for (CartItem item : cart.getItems()) {
            OrderDetail orderDetail = new OrderDetail(item.getProduct(), item.getQuantity());
            order.getOrderDetails().add(orderDetail);
        }

        orderRepository.save(order);
    }
}
