package com.example.shoppingmall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private LocalDateTime orderDate;

    //총 주문수량 | 총 주문액
    private long totalQuantity;
    private long totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private LocalDateTime statusChangeDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)    // 양방향관계, 연관관계의 주인
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProduct;

    //편의 매서드는 차후 구현
    //주문생성(회원, 주문일자, 상태, 재고(minus), 배송, 주문상품 - 주문 총수량, 총금액)
    
    //주문취소(주문취소일자, 상태, 재고(plus), 배송)

}
