package com.example.shoppingmall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    @Column(length = 20, unique = true)
    private String productName;
    @Column(nullable = false)
    private int cost;               // 상품원가
    private int price;              // 상품판매가격
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    private LocalDate registrationDate;
    private LocalDate changeStatusDate;

    //편의 매서드
    //가격 변경 불가
    public void changeStatus(ProductStatus status) {
        this.status = status;
        this.changeStatusDate = LocalDate.now();
    }

}
