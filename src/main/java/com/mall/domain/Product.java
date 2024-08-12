package com.mall.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@ToString(exclude = "imageList")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String pname;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private String pdesc;
    @Column(nullable = false)
    private boolean delFlag;
    @ElementCollection
    @Builder.Default
    private List<ProductImage> imageList = new ArrayList<>();
}
