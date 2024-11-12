package com.example.shoppingmall.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDto {
    @NotNull
    private String productName;
    @NotNull
    private Integer cost;
    @NotNull
    private Integer price;
}
