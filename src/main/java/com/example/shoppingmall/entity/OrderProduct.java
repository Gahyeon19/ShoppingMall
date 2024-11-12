package com.example.shoppingmall.entity;

import com.example.shoppingmall.exception.OrderCancelException;
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

    public static OrderProduct createOrderProduct(Product product, int quantity, int price, Stock stock) {
        OrderProduct orderProduct = new OrderProduct(null, null, product, quantity, price, OrderProductStatus.ORDERED);
        stock.decreaseStock(quantity);
        return orderProduct;
    }

    //취소 : status 변경, order의 status가 ordered이거나 partialCanceled인 경우에만 취소 가능
    //재고가 +되어야 하고, order의 status와 statusChangeDate가 변경되어야 하고 order의 totalQuantity와 totalPrice가 변경되어야 함
    public void cancelOrderProducts(Stock stock) {
        if (orders.getOrderStatus() == OrderStatus.ORDERED || orders.getOrderStatus() == OrderStatus.PARTIALCANCELED) {
            this.orderProductStatus = OrderProductStatus.CANCELED;
            stock.increaseStock(quantity);
//            orders.partialOrderCancel(this);    // 무한루프?
        } else {
            throw new OrderCancelException("주문완료 상태이거나 부분취소 상태인 경우에만 취소가능합니다. 고객센터 문의 바람.");
        }
    }

}
