package com.example.shoppingmall.dto;

import com.example.shoppingmall.entity.WareHouse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockCreateDto {
    private WareHouse wareHouse;
    private int productId;
    private long quantity;
}
