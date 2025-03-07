package com.nadeeshaabey.shopping_cart.service.cart;

import com.nadeeshaabey.shopping_cart.model.Cart;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
}
