package com.example.shoppingmall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;   // 각 제품별 구입수량
    private int price;      // 각 제품별 가격

    @Enumerated(EnumType.STRING)
    private OrderProductStatus orderProductStatus;

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    //최소 : status 변경, order의 status가 ordered이거나 partialCanceled인 경우에만 취소 가능
    //재고가 +되어야 하고, order의 status와 statusChangeDate가 변경되어야 하고 order의 totalQuantity와 totalPrice가 변경되어야 함
    public void cancelOrderProduct(OrderProductStatus orderProductStatus) {
        this.orderProductStatus = orderProductStatus;
    }
}
