package com.example.shoppingmall.service;

import com.example.shoppingmall.dto.product.ProductCreateDto;
import com.example.shoppingmall.dto.product.ProductInquiryDto;
import com.example.shoppingmall.entity.Product;
import com.example.shoppingmall.entity.ProductStatus;
import com.example.shoppingmall.exception.NotUniqueProductNameException;
import com.example.shoppingmall.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductInquiryDto addProduct(ProductCreateDto productDto) {
        if (checkUniqueProductName(productDto.getProductName())) {
            Product product = new Product(
                    0,
                    productDto.getProductName(),
                    productDto.getCost(),
                    productDto.getPrice(),
                    ProductStatus.ACTIVE,
                    LocalDate.now(),
                    LocalDate.now());
            Product save = productRepository.save(product);
            return ProductInquiryDto.of(save);
        }
        return null;
    }

    boolean checkUniqueProductName(String productName) {
        Optional<Product> product = productRepository.findByProductName(productName);
        if (product.isPresent()) {
            throw new NotUniqueProductNameException("이미 등록된 상품입니다.");
        }
        return true;
    }

}
