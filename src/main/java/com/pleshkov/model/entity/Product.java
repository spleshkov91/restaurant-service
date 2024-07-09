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
public class Product {

    private int id;
    private String name;
    private BigDecimal price;
    private int quantity;
    private List<ProductCategory> categoryList;
}
