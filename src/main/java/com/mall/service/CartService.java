package com.mall.service;

import com.mall.domain.Cart;
import com.mall.domain.CartItem;
import com.mall.domain.Member;
import com.mall.domain.Product;
import com.mall.dto.cart.CartItemDto;
import com.mall.dto.cart.CartItemListDto;
import com.mall.exception.ErrorCode;
import com.mall.exception.NotFoundException;
import com.mall.repository.CartItemRepository;
import com.mall.repository.CartRepository;
import com.mall.repository.MemberRepository;
import com.mall.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public List<CartItemListDto> addOrModifyCartItem(CartItemDto cartItemDto) {
        String email = cartItemDto.getEmail();
        Optional<CartItem> existingCartItem = cartItemDto.getCarItemId() != null ?
                cartItemRepository.findById(UUID.fromString(cartItemDto.getCarItemId())) : Optional.empty();

        CartItem cartItem = existingCartItem
                .map(item -> updateCartItemQty(item, cartItemDto.getQty()))
                .orElseGet(() -> createCartItem(cartItemDto, email));

        cartItemRepository.save(cartItem);
        return getCartItems(email);
    }

    private CartItem updateCartItemQty(CartItem item, int qty) {
        item.changeQty(qty);
        return item;
    }

    private CartItem createCartItem(CartItemDto cartItemDto, String email) {
        Cart cart = getOrCreateCart(email);
        UUID productId = UUID.fromString(cartItemDto.getProductId());
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

        return CartItem.builder()
                .product(product)
                .cart(cart)
                .qty(cartItemDto.getQty())
                .build();
    }

    private Cart getOrCreateCart(String email) {
        return cartRepository.findByOwner_Email(email)
                .orElseGet(() -> createNewCart(email));
    }

    private Cart createNewCart(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        Cart newCart = Cart.builder().owner(member).build();
        Cart savedCart = cartRepository.save(newCart);
        return savedCart;
    }

    public List<CartItemListDto> getCartItems(String email) {
        return cartItemRepository.getItemsOfCartDTOByEmail(email);
    }

    public Optional<Cart> getCart(String email) {
        return cartRepository.findByOwner_Email(email);
    }
}
