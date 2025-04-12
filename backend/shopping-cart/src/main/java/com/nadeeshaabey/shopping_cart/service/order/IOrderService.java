package com.nadeeshaabey.shopping_cart.service.order;

import com.nadeeshaabey.shopping_cart.dto.OrderDTO;
import com.nadeeshaabey.shopping_cart.model.Order;

import java.util.List;

public interface IOrderService {

    Order placeOrder(Long userId);
    OrderDTO getOrder(Long orderId);

    List<OrderDTO> getUserOrders(Long userId);
}


