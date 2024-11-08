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
    private List<OrderProduct> orderProducts;

    //주문생성(회원, 주문일자, 상태, 재고(minus), 배송, 주문상품 - 주문 총수량, 총금액)
    public static Orders createOrders(Member member, Delivery delivery, OrderProduct... orderProducts) {
        Orders orders = new Orders(null, member, LocalDateTime.now(), 0, 0,
                OrderStatus.ORDERED, LocalDateTime.now(), delivery, new ArrayList<>());
        for (OrderProduct op : orderProducts) {
            orders.totalQuantity += op.getQuantity();
            orders.totalPrice += op.getPrice() * op.getQuantity();
            op.setOrders(orders);
            orders.orderProducts.add(op);

        }
        delivery.setOrders(orders);
        return orders;
    }
    //주문취소(주문취소일자, 상태, 재고(plus), 배송)
    //주문 전체 취소
    public void totalOrderCancel() {
        if (this.orderStatus == OrderStatus.ORDERED || this.orderStatus == OrderStatus.PARTIALCANCELED) {
            this.orderStatus = OrderStatus.TOTALCANCELED;
            this.statusChangeDate = LocalDateTime.now();
            this.totalQuantity = 0;
            this.totalPrice = 0;
        }
    }

    //주문 부분 취소
    public void partialOrderCancel(OrderProduct orderProduct) {
        if (this.orderStatus == OrderStatus.ORDERED || this.orderStatus == OrderStatus.PARTIALCANCELED) {
            this.orderStatus = OrderStatus.PARTIALCANCELED;
            this.statusChangeDate = LocalDateTime.now();
            this.totalQuantity -= orderProduct.getQuantity();
            this.totalPrice -= orderProduct.getPrice() * orderProduct.getQuantity();
        }
    }
}
