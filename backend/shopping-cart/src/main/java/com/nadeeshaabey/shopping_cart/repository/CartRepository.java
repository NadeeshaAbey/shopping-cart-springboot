package com.nadeeshaabey.shopping_cart.repository;

import com.nadeeshaabey.shopping_cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
