package com.nadeeshaabey.shopping_cart.dto;

import com.nadeeshaabey.shopping_cart.model.Cart;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private List<OrderDTO> orders;
    private CartDTO cart;
}


