package com.example.shoppingmall.dto.order;

import com.example.shoppingmall.entity.OrderStatus;
import com.example.shoppingmall.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderInquiryDto {
    private long orderId;
    private int memberId;
    private String memberName;
    private LocalDateTime orderDate;
    private long totalQuantity;
    private long totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime statusChangeDate;

    public static OrderInquiryDto of(Orders order) {
        OrderInquiryDto dto = new OrderInquiryDto();
        dto.setOrderId(order.getOrderId());
        dto.setMemberId(order.getMember().getMemberId());
        dto.setMemberName(order.getMember().getMemberName());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalQuantity(order.getTotalQuantity());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setStatusChangeDate(order.getStatusChangeDate());
        return dto;
    }

    @Override
    public String toString() {
        return "OrderInquiryDto{" +
                "orderId=" + orderId +
                ", memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", orderDate=" + orderDate +
                ", totalQuantity=" + totalQuantity +
                ", totalPrice=" + totalPrice +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
