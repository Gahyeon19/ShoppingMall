package com.example.shoppingmall.repository;

import com.example.shoppingmall.entity.Member;
import com.example.shoppingmall.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByMember(Member member);
}
