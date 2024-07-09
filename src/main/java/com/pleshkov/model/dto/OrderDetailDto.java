package com.pleshkov.model.dto;

import com.pleshkov.model.entity.OrderStatus;
import com.pleshkov.model.entity.Product;
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
public class OrderDetailDto {

    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private List<Product> productList;

}
