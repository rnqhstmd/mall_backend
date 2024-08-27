package com.mall.service;

import com.mall.domain.Product;
import com.mall.domain.ProductImage;
import com.mall.dto.page.PageRequestDto;
import com.mall.dto.page.PageResponseDto;
import com.mall.dto.product.ProductDto;
import com.mall.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public PageResponseDto<ProductDto> getList(PageRequestDto pageRequestDto) {
        Pageable pageable = PageRequest.of(
                pageRequestDto.getPage(), pageRequestDto.getSize(), Sort.by("createdAt").descending()
        );
        Page<Object[]> result = productRepository.selectList(pageable);
        List<ProductDto> dtoList = result.getContent().stream().map(arr -> {
            Product product = (Product) arr[0];
            ProductImage productImage = (ProductImage) arr[1];
            String imageStr = productImage.getFileName();
            return ProductDto.builder()
                    .productId(String.valueOf(product.getId()))
                    .pname(product.getPname())
                    .pdesc(product.getPdesc())
                    .price(product.getPrice())
                    .uploadFileNames(List.of(imageStr))
                    .build();
        }).collect(Collectors.toList());
        return PageResponseDto.<ProductDto>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDto)
                .totalCount(result.getTotalElements())
                .build();
    }
}
