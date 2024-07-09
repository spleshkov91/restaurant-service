package com.pleshkov.dao;


import com.pleshkov.model.entity.Product;

import java.util.List;

public interface ProductDao {
    void insertProduct(Product product);

    boolean updateProduct(Product product);

    Product selectProductById(int id);

    List<Product> selectAllProducts();

    boolean deleteProduct(int id);
}
