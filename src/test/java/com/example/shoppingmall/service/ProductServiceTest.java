package com.example.shoppingmall.service;

import com.example.shoppingmall.dto.ProductCreateDto;
import com.example.shoppingmall.exception.NotUniqueProductNameException;
import com.example.shoppingmall.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceTest {
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;

    @Test
    public void 제품등록테스트() {
        //Given
        ProductCreateDto productDto1 = new ProductCreateDto(
                "test1", 100, 120
        );
        ProductCreateDto productDto2 = new ProductCreateDto(
                "test1", 100, 120
        );
        //When
        productService.addProduct(productDto1);
        //Then
        Assertions.assertThat(productRepository.findAll().size()).isEqualTo(1);

        //When
        //Then
        org.junit.jupiter.api.Assertions.assertThrows(NotUniqueProductNameException.class, () -> {
            productService.addProduct(productDto2);
        });

    }
}