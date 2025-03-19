package com.nadeeshaabey.shopping_cart.service.order;

import com.nadeeshaabey.shopping_cart.model.Order;

public interface IOrderService {

    Order placeOrder(Long userId);
    Order getOrder(Long orderId);
}


