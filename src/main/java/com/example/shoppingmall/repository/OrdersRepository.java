package com.example.shoppingmall.repository;

import com.example.shoppingmall.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
