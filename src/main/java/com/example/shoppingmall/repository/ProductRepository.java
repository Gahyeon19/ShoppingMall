package com.example.shoppingmall.repository;

import com.example.shoppingmall.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
