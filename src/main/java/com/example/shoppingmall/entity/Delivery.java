package com.example.shoppingmall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "delivery")    // 양방향관계
    private Orders orders;
    @Column(length = 100)
    private String address;
    private DeliveryStatus deliveryStatus;
    private LocalDateTime deliveryTime;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    //편의 매서드
    public void changeStatus(DeliveryStatus status) {
        this.deliveryStatus = status;
        this.deliveryTime = LocalDateTime.now();
    }
}
