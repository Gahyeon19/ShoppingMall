package com.example.shoppingmall.repository;

import com.example.shoppingmall.entity.Product;
import com.example.shoppingmall.entity.Stock;
import com.example.shoppingmall.entity.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Integer> {
    Optional<Stock> findByWareHouseAndProduct(WareHouse wareHouse, Product product);
}
