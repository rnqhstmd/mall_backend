package com.mall.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Test
    void selectList() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        Page<Object[]> result = productRepository.selectList(pageable);
        result.getContent().forEach(arr -> log.info(Arrays.toString(arr)));
    }
}