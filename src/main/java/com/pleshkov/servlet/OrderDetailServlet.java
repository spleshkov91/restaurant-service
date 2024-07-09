package com.pleshkov.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.pleshkov.dao.impl.OrderDetailDaoImpl;
import com.pleshkov.mapper.OrderDetailMapper;
import com.pleshkov.mapper.OrderDetailMapperImpl;
import com.pleshkov.model.dto.OrderDetailDto;
import com.pleshkov.model.entity.OrderDetail;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/order/*")
public class OrderDetailServlet extends HttpServlet {

    private final transient OrderDetailDaoImpl orderDetailDao;
    private final transient OrderDetailMapper mapper;
    private final ObjectMapper objectMapper;

    public OrderDetailServlet() {
        this.orderDetailDao = new OrderDetailDaoImpl();
        this.mapper = new OrderDetailMapperImpl();
        this.objectMapper = new JsonMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getPathInfo();

        try {
            switch (action) {
                case "/id":
                    selectOrderById(req, resp);
                    break;
                case "/delete":
                    deleteOrder(req, resp);
                    break;
                default:
                    listOrders(req, resp);
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void listOrders(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<OrderDetailDto> orderDetailDtoList = orderDetailDao.selectAllOrders().stream()
                .map(mapper::orderDetailToOrderDetailDto)
                .collect(Collectors.toList());
        String jsonProductDtoList = objectMapper.writeValueAsString(orderDetailDtoList);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonProductDtoList);
    }

    private void selectOrderById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        OrderDetail orderDetail = orderDetailDao.selectOrderById(id);
        OrderDetailDto orderDetailDto = mapper.orderDetailToOrderDetailDto(orderDetail);
        String jsonOrderDetailDto = objectMapper.writeValueAsString(orderDetailDto);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonOrderDetailDto);
    }
    private void deleteOrder(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        orderDetailDao.deleteOrder(id);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}

