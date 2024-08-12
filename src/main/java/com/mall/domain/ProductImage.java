package com.mall.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Embeddable
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage extends BaseEntity {
    @Column(nullable = false)
    private String fileName;
    @Column(nullable = false)
    private int ord;

    public void setOrd(int ord) {
        this.ord = ord;
    }
}
