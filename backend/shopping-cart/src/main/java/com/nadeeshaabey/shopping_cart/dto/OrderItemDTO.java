package com.nadeeshaabey.shopping_cart.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {

    private Long productId;
    private String productName;
    private String productBrand;
    private int quantity;
    private BigDecimal price;
}
