package com.nadeeshaabey.shopping_cart.repository;

import com.nadeeshaabey.shopping_cart.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRpository extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long id);
}
