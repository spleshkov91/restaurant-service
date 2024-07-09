package com.pleshkov.servlet;


import com.pleshkov.TestUtil;
import com.pleshkov.model.entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductServletTest {

    @Mock
    ConcurrentHashMap<Integer, Product> products = mock(ConcurrentHashMap.class);
    @InjectMocks
    ProductServlet servlet = mock(ProductServlet.class);
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse resp = mock(HttpServletResponse.class);


    @Test
    void doPost() throws ServletException, IOException {
        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("name")).thenReturn("green tea");
        when(req.getParameter("price")).thenReturn("200");
        when(req.getParameter("quantity")).thenReturn("5");

        servlet.doPost(req, resp);
        verify(req, times(1));

    }

    @Test
    void doGet() throws ServletException, IOException {
        Product product = TestUtil.product1;
        products.put(product.getId(), product);
        String path = "/product/id/";

        when(req.getPathInfo()).thenReturn(path);
        when(products.get(1)).thenReturn(product);
        servlet.doGet(req, resp);
        verify(req, times(1));
    }
}