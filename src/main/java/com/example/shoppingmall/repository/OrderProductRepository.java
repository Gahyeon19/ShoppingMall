package com.example.shoppingmall.repository;

import com.example.shoppingmall.entity.OrderProduct;
import com.example.shoppingmall.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    List<OrderProduct> findAllByOrders(Orders order);
}
