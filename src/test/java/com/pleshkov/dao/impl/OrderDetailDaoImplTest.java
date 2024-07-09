package com.pleshkov.dao.impl;

import com.pleshkov.TestUtil;
import com.pleshkov.config.ContainerUtil;
import com.pleshkov.model.entity.OrderDetail;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Testcontainers
class OrderDetailDaoImplTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = ContainerUtil.run();
    private OrderDetailDaoImpl orderDetailDao;

    public OrderDetailDaoImplTest() {
        this.orderDetailDao = new OrderDetailDaoImpl();
    }

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        ContainerUtil.stop();
    }

    @Test
    void testConnect() {
        assertTrue(ContainerUtil.postgreSQLContainer.isRunning());
    }

    @Test
    void selectAllOrders() {
        List<OrderDetail> orderDetails = orderDetailDao.selectAllOrders();

        assertEquals(3, orderDetails.size());
    }

    @Test
    void selectOrderById() {
        OrderDetail firstOrder = TestUtil.orderDetail1;
        OrderDetail secondOrder = orderDetailDao.selectOrderById(1);
        assertEquals(firstOrder, secondOrder);
    }

    @Test
    void totalAmountOrder() {
        OrderDetail firstOrder = TestUtil.orderDetail1;
        OrderDetail secondOrder = orderDetailDao.selectOrderById(1);
        assertEquals(firstOrder.getTotalAmount(), secondOrder.getTotalAmount());

    }

    @Test
    void deleteOrder() {
        orderDetailDao.deleteOrder(3);
        List<OrderDetail> orderDetails = orderDetailDao.selectAllOrders();

        assertEquals(2, orderDetails.size());
    }
}