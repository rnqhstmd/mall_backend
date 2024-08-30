package com.mall.repository;

import com.mall.domain.CartItem;
import com.mall.dto.cart.CartItemListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    @Query("select" +
            " ci " +
            " from " +
            " CartItem ci inner join Cart c on ci.cart = c " +
            " where " +
            " c.owner.email = :email and ci.product.id = :pno")
    CartItem getItemOfPno(@Param("email") String email, @Param("pno") UUID pno);

    @Query("select " +
            " new com.mall.dto.cart.CartItemListDto(ci.id, ci.qty, p.id, p.pname, p.price , pi.fileName ) " +
            " from " +
            " CartItem ci inner join Cart mc on ci.cart = mc " +
            " left join Product p on ci.product = p " +
            " left join p.imageList pi" +
            " where " +
            " mc.owner.email = :email and pi.ord = 0 " +
            " order by ci.createdAt desc ")
    List<CartItemListDto> getItemsOfCartDTOByEmail(@Param("email") String email);

    @Query("select " +
            " c.id " +
            "from " +
            " Cart c inner join CartItem ci on ci.cart = c " +
            " where " +
            " ci.id = :cino")
    UUID getCartFromItem(@Param("cino") UUID cino);
}
