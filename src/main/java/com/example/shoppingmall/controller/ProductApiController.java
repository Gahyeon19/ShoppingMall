package com.example.shoppingmall.controller;

import com.example.shoppingmall.dto.product.ProductCreateDto;
import com.example.shoppingmall.dto.product.ProductInquiryDto;
import com.example.shoppingmall.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductApiController {
    private final ProductService productService;

    @GetMapping
    public List<ProductInquiryDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{prodId}")
    public ProductInquiryDto getOneProduct(@PathVariable("prodId") int prodId) {
        return productService.getOneProduct(prodId);
    }

    @PostMapping
    public ProductInquiryDto createProduct(@RequestBody ProductCreateDto productDto) {
        return productService.addProduct(productDto);
    }
}
