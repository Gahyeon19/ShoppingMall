package com.example.shoppingmall.dto.order;

import jakarta.persistence.Access;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductCreateDto {
    //orderProduct
    private int productId;
    private int quantity;
}
