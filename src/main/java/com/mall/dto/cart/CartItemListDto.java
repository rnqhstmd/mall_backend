package com.mall.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CartItemListDto {
    private UUID cartItemId;
    private int qty;
    private UUID productId;
    private String pname;
    private int price;
    private String imageFile;
}
