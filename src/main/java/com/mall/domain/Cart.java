package com.mall.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_cart", indexes = {@Index(name = "idx_cart_email", columnList = "member_owner")})
public class Cart extends BaseEntity {
    @OneToOne
    @JoinColumn(name="member_owner")
    private Member owner;
}
