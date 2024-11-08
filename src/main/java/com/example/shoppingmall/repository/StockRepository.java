package com.example.shoppingmall.repository;

import com.example.shoppingmall.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Integer> {
}
