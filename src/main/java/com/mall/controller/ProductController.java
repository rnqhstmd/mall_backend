package com.mall.controller;

import com.mall.dto.common.ResponseDto;
import com.mall.dto.product.ProductDto;
import com.mall.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final FileUtil fileUtil;

    @PostMapping
    public ResponseEntity<ResponseDto<Map<String, String>>> register(ProductDto productDto) {
        List<MultipartFile> files = productDto.getFiles();
        List<String> uploadFileNames = fileUtil.saveFiles(files);
        ProductDto.builder()
                .uploadFileNames(uploadFileNames)
                .build();
        log.info(uploadFileNames.toString());
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "Product 생성 완료"), HttpStatus.CREATED);
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<ResponseDto<Resource>> getFile(@PathVariable String fileName) {
        Resource resource = fileUtil.getFile(fileName);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "파일 불러오기 완료", resource), HttpStatus.OK);
    }
}
