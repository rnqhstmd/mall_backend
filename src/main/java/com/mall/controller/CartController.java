package com.mall.controller;

import com.mall.dto.cart.CartItemDto;
import com.mall.dto.cart.CartItemListDto;
import com.mall.dto.common.ResponseDto;
import com.mall.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/items")
    public ResponseEntity<ResponseDto<List<CartItemListDto>>> getCartItems(Principal principal) {
        List<CartItemListDto> cartItemListDtos = cartService.getCartItems(principal.getName());
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "장바구니 조회 완료", cartItemListDtos), HttpStatus.OK);
    }

    @PreAuthorize("#itemDto.email==authentication.name")
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> changeCart(@RequestBody CartItemDto cartItemDto) {
        cartService.addOrModifyCartItem(cartItemDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "장바구니 아이템 수정 완료"), HttpStatus.OK);
    }
}
