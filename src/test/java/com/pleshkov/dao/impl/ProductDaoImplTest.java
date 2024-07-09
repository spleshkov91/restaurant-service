package com.pleshkov.dao.impl;

import com.pleshkov.TestUtil;
import com.pleshkov.config.ContainerUtil;
import com.pleshkov.model.entity.Product;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
class ProductDaoImplTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = ContainerUtil.run();
    private ProductDaoImpl productDao;

    public ProductDaoImplTest() {
        this.productDao = new ProductDaoImpl();
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
    void insertProduct() {
        Product product = Product.builder()
                .name("test product")
                .price(BigDecimal.valueOf(100))
                .quantity(3)
                .build();
        productDao.insertProduct(product);
        List<Product> products = productDao.selectAllProducts();
        assertEquals(7, products.size());
    }

    @Test
    void updateProduct() {

        List<Product> products = productDao.selectAllProducts();
        Product product = products.get(1);
        product.setName("new name");
        productDao.updateProduct(product);

        assertEquals("new name", products.get(1).getName());
    }

    @Test
    void selectProductById() {

        Product firstProduct = TestUtil.product1;
        Product secondProduct = productDao.selectProductById(1);
        assertEquals(firstProduct, secondProduct);
    }

    @Test
    void selectAllProducts() {
        List<Product> productList = productDao.selectAllProducts();

        assertEquals(5, productList.size());
    }

    @Test
    void deleteProduct() {
        productDao.deleteProduct(6);

        List<Product> products = productDao.selectAllProducts();
        assertEquals(5, products.size());
    }
}