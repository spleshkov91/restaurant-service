package com.pleshkov;

import com.pleshkov.model.entity.OrderDetail;
import com.pleshkov.model.entity.OrderStatus;
import com.pleshkov.model.entity.Product;
import com.pleshkov.model.entity.ProductCategory;

import java.math.BigDecimal;
import java.util.List;

public class TestUtil {

    public static ProductCategory productCategory1 = ProductCategory.builder()
            .id(1)
            .name("cold")
            .build();

    public static ProductCategory productCategory2 = ProductCategory.builder()
            .id(2)
            .name("hot")
            .build();

    public static ProductCategory productCategory3 = ProductCategory.builder()
            .id(3)
            .name("alcohol")
            .build();

    public static ProductCategory productCategory4 = ProductCategory.builder()
            .id(4)
            .name("non-alcohol")
            .build();

    public static Product product1 = Product.builder()
            .id(1)
            .name("green tea")
            .price(BigDecimal.valueOf(200))
            .quantity(5)
            .categoryList(List.of(productCategory1, productCategory4))
            .build();

    public static Product product2 = Product.builder()
            .id(2)
            .name("fresh coctail")
            .price(BigDecimal.valueOf(300))
            .quantity(3)
            .categoryList(List.of(productCategory1, productCategory4))
            .build();

    public static Product product3 = Product.builder()
            .id(3)
            .name("coffe")
            .price(BigDecimal.valueOf(150))
            .quantity(7)
            .build();

    public static Product product4 = Product.builder()
            .id(4)
            .name("beer")
            .price(BigDecimal.valueOf(250))
            .quantity(3)
            .categoryList(List.of(productCategory3))
            .build();

    public static Product product5 = Product.builder()
            .id(5)
            .name("vodka")
            .price(BigDecimal.valueOf(250))
            .quantity(3)
            .build();

    public static List<Product> allProducts = List.of(product1, product2,
            product3, product4, product5);

    public static OrderDetail orderDetail1 = OrderDetail.builder()
            .id(1)
            .orderStatus(OrderStatus.READY)
            .totalAmount(BigDecimal.valueOf(500))
            .productList(List.of(product1, product2))
            .build();

}
