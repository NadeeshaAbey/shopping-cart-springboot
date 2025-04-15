package com.nadeeshaabey.shopping_cart.repository;

import com.nadeeshaabey.shopping_cart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    User findByEmail(String email);


}
