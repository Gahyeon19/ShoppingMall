package com.example.shoppingmall.dto;

import com.example.shoppingmall.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInquiryDto {
    private int productId;
    private String productName;
    private int price;

    public static ProductInquiryDto of(Product product) {
        ProductInquiryDto dto = new ProductInquiryDto(
                product.getProductId(),
                product.getProductName(),
                product.getPrice()
        );
        return dto;
    }
}
