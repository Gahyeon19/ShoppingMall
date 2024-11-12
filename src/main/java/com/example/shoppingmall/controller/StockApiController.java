package com.example.shoppingmall.controller;

import com.example.shoppingmall.dto.stock.StockCreateDto;
import com.example.shoppingmall.dto.stock.StockInquiryDto;
import com.example.shoppingmall.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stocks")
public class StockApiController {
    private final StockService stockService;

    @GetMapping("/{prodId}")
    public StockInquiryDto getOneStock(@PathVariable("prodId") int prodId) {
        return stockService.getOneStock(prodId);
    }

    @PostMapping
    public void createStock(@RequestBody StockCreateDto stockDto) {
        int stockId = stockService.addStock(stockDto);
        if(stockId == 0) {
            throw new RuntimeException("재고자료 생성 오류");
        };
    }
}
