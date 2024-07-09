package com.pleshkov.dao;

import com.pleshkov.model.entity.OrderDetail;

import java.math.BigDecimal;
import java.util.List;

public interface OrderDetailDao {
    List<OrderDetail> selectAllOrders();

    OrderDetail selectOrderById(int id);

    BigDecimal totalAmountOrder(int id);

    boolean deleteOrder(int id);

}
