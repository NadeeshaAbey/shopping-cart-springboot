package com.nadeeshaabey.shopping_cart.repository;

import com.nadeeshaabey.shopping_cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
