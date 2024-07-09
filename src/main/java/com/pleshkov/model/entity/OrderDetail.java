package com.pleshkov.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    private int id;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private List<Product> productList;

}
