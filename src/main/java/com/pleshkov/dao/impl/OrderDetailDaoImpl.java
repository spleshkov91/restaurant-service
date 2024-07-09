package com.pleshkov.dao.impl;

import com.pleshkov.config.ConnectionManagerImpl;
import com.pleshkov.dao.OrderDetailDao;
import com.pleshkov.dao.exceptions.DaoException;
import com.pleshkov.dao.sql_query.OrderDetailQuery;
import com.pleshkov.model.entity.OrderDetail;
import com.pleshkov.model.entity.OrderStatus;
import com.pleshkov.model.entity.Product;
import lombok.extern.slf4j.Slf4j;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class OrderDetailDaoImpl implements OrderDetailDao {

    private final ConnectionManagerImpl manager;

    public OrderDetailDaoImpl() {
        this.manager = new ConnectionManagerImpl();
    }

    @Override
    public List<OrderDetail> selectAllOrders() {
        List<OrderDetail> orderDetailList;
        try (Connection connection = manager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(OrderDetailQuery.SELECT_ALL_ORDERS.getQuery())) {
            ResultSet resultSet = preparedStatement.executeQuery();
            orderDetailList = parseProductsFromResultSet(resultSet);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DaoException(e);
        }

        return orderDetailList;
    }

    @Override
    public OrderDetail selectOrderById(int id) {
        OrderDetail orderDetail = null;
        try (Connection connection = manager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(OrderDetailQuery.SELECT_ORDER_BY_ID.getQuery())) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            orderDetail = parseProductsFromResultSet(resultSet).get(0);

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DaoException(e);
        }
        return orderDetail;
    }

    @Override
    public BigDecimal totalAmountOrder(int id) {
        BigDecimal totalAmount = null;
        try (Connection connection = manager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(OrderDetailQuery.TOTAL_AMOUNT_ORDER.getQuery())) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                totalAmount = resultSet.getBigDecimal("sum");
            }

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DaoException(e);
        }

        return totalAmount;
    }

    @Override
    public boolean deleteOrder(int id) {
        boolean rowDeleted;
        try (Connection connection = manager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(OrderDetailQuery.DELETE_ORDER.getQuery())) {
            preparedStatement.setInt(1, id);

            rowDeleted = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DaoException(e);
        }
        return rowDeleted;
    }


    private List<OrderDetail> parseProductsFromResultSet(ResultSet resultSet) throws SQLException {
        final List<Product> products = new ArrayList<>();
        final Map<Integer, OrderDetail> map = new HashMap<>();
        int previousId = -1;

        while (resultSet.next()) {
            int currentId = resultSet.getInt("order_id");
            OrderDetail orderDetail = map.get(currentId);
            if (Optional.ofNullable(orderDetail).isEmpty()) {
                Optional.ofNullable(map.get(previousId)).ifPresent(ordr -> ordr.setProductList(new ArrayList<>(products)));
                products.clear();
                orderDetail = OrderDetail.builder()
                        .id(currentId)
                        .orderStatus(OrderStatus.valueOf(resultSet.getString("order_status")))
                        .totalAmount(totalAmountOrder(currentId))
                        .build();
            }

            products.add(Product.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .price(resultSet.getBigDecimal("price"))
                    .quantity(resultSet.getInt("quantity"))
                    .build());
            map.put(currentId, orderDetail);
            previousId = resultSet.getInt("order_id");
        }

        Optional.ofNullable(map.get(previousId)).ifPresent(ordr -> ordr.setProductList(new ArrayList<>(products)));
        return map.values().stream().toList();
    }

}
