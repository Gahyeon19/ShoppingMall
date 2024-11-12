package com.example.shoppingmall.dto.stock;

import com.example.shoppingmall.entity.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockInquiryDto {
    private int stockId;
    private int productId;
    private long quantity;

    public static StockInquiryDto of(Stock stock) {
        StockInquiryDto dto = new StockInquiryDto(
                stock.getStockId(),
                stock.getProduct().getProductId(),
                stock.getQuantity()
        );
        return dto;
    }
}
