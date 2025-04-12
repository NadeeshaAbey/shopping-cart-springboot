package com.nadeeshaabey.shopping_cart.dto;

import com.nadeeshaabey.shopping_cart.model.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDTO {
    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductDTO product;
}
