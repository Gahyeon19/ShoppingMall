package com.example.shoppingmall.service;

import com.example.shoppingmall.dto.stock.StockCreateDto;
import com.example.shoppingmall.dto.stock.StockInquiryDto;
import com.example.shoppingmall.entity.Product;
import com.example.shoppingmall.entity.Stock;
import com.example.shoppingmall.entity.WareHouse;
import com.example.shoppingmall.exception.NotUniqueStockException;
import com.example.shoppingmall.repository.ProductRepository;
import com.example.shoppingmall.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StockService {
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    public StockInquiryDto getOneStock(int productId){
        Optional<Product> optProd = productRepository.findById(productId);
        if(optProd.isPresent()){
            Product product = optProd.get();
            Optional<Stock> optStock = stockRepository.findByWareHouseAndProduct(WareHouse.KR, product);
            if(optStock.isPresent()){
                return StockInquiryDto.of(optStock.get());
            }
        }
        return null;
    }

    public int addStock(StockCreateDto stockDto) {
        Product product = productRepository.findById(stockDto.getProductId()).get();
        if(checkUniqueStock(stockDto.getWareHouse(), product)) {
            Stock stock = new Stock(
                    0,
                    stockDto.getWareHouse(),
                    product,
                    stockDto.getQuantity()
            );
            Stock save = stockRepository.save(stock);
            return save.getStockId();
        }
        return 0;
    }

    boolean checkUniqueStock(WareHouse wareHouse, Product product) {
        Optional<Stock> byStock = stockRepository.findByWareHouseAndProduct(wareHouse, product);
        if(byStock.isPresent()) {
            throw new NotUniqueStockException("이미 재고자료가 존재합니다.");
        }
        return true;
    }
}
