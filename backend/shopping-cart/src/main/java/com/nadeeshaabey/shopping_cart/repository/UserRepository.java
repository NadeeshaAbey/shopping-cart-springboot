package com.nadeeshaabey.shopping_cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository {

    boolean existsByEmail(String email);
}
