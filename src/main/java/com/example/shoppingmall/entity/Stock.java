package com.example.shoppingmall.entity;

import com.example.shoppingmall.exception.NoEnoughStockException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stockId;
    @Enumerated(EnumType.STRING)
    private WareHouse wareHouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    private long quantity;

    //편의 매서드
    // 수량증가, 수량감소(주문취소 시, 주문완료 시)
    public void increaseStock(long quantity) {
        this.quantity += quantity;
    }

    public void decreaseStock(long quantity) {
        if (this.quantity < quantity) {     // 재고보다 주문량이 많으면 재고부족 exception 발생
            throw new NoEnoughStockException("재고가 부족합니다.");
        } else {
            this.quantity -= quantity;
        }
    }
}
