package com.pleshkov.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.pleshkov.dao.impl.ProductDaoImpl;
import com.pleshkov.mapper.ProductMapper;
import com.pleshkov.mapper.ProductMapperImpl;
import com.pleshkov.model.dto.ProductDto;
import com.pleshkov.model.entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/product/*")
public class ProductServlet extends HttpServlet {


    private final transient ProductDaoImpl productDao;
    private final transient ProductMapper mapper;
    private final ObjectMapper objectMapper;

    public ProductServlet() {
        this.productDao = new ProductDaoImpl();
        this.mapper = new ProductMapperImpl();
        this.objectMapper = new JsonMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();

        try {
            switch (action) {
                case "/id":
                    selectProductById(req, resp);
                    break;
                case "/delete":
                    deleteProduct(req, resp);
                    break;
                case "/insert":
                    insertProduct(req, resp);
                    break;
                case "/update":
                    updateProduct(req, resp);
                    break;
                default:
                    listProduct(req, resp);
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void listProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<ProductDto> productDtoList = productDao.selectAllProducts().stream()
                .map(mapper::productToProductDto)
                .collect(Collectors.toList());
        String jsonProductDtoList = objectMapper.writeValueAsString(productDtoList);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonProductDtoList);
    }

    private void selectProductById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = productDao.selectProductById(id);
        ProductDto productDto = mapper.productToProductDto(product);
        String jsonProductDto = objectMapper.writeValueAsString(productDto);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonProductDto);
    }

    private void insertProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        productDao.insertProduct(mapper.productDtoToProduct(objectMapper.readValue(req.getInputStream(), ProductDto.class)));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        productDao.updateProduct(mapper.productDtoToProduct(objectMapper.readValue(req.getInputStream(), ProductDto.class)));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        productDao.deleteProduct(id);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
