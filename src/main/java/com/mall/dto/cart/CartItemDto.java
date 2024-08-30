package com.mall.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartItemDto {
    private String email;
    private String productId;
    private int qty;
    private String carItemId;
}
