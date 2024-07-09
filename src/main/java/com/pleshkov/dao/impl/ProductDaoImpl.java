package com.pleshkov.dao.impl;

import com.pleshkov.config.ConnectionManagerImpl;
import com.pleshkov.dao.ProductDao;
import com.pleshkov.dao.exceptions.DaoException;
import com.pleshkov.dao.sql_query.ProductSqlQuery;
import com.pleshkov.model.entity.Product;
import com.pleshkov.model.entity.ProductCategory;
import lombok.extern.slf4j.Slf4j;

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
public class ProductDaoImpl implements ProductDao {

    private final ConnectionManagerImpl manager;

    public ProductDaoImpl() {
        this.manager = new ConnectionManagerImpl();
    }

    @Override
    public void insertProduct(Product product) {
        try (Connection connection = manager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ProductSqlQuery.INSERT_PRODUCT.getQuery())) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean updateProduct(Product product) {
        boolean rowUpdated;
        try (Connection connection = manager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ProductSqlQuery.UPDATE_PRODUCT.getQuery())) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setInt(4, product.getId());

            rowUpdated = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DaoException(e);
        }
        return rowUpdated;
    }

    @Override
    public Product selectProductById(int id) {
        Product product = null;
        try (Connection connection = manager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ProductSqlQuery.SELECT_PRODUCT_BY_ID.getQuery())) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            product = parseCategoryFromResultSet(resultSet).get(0);

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DaoException(e);
        }
        return product;
    }

    @Override
    public List<Product> selectAllProducts() {
        List<Product> products;

        try (Connection connection = manager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ProductSqlQuery.SELECT_ALL_PRODUCTS.getQuery())) {
            ResultSet resultSet = preparedStatement.executeQuery();
            products = parseCategoryFromResultSet(resultSet);


        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DaoException(e);
        }
        return products;
    }

    @Override
    public boolean deleteProduct(int id) {
        boolean rowDeleted;
        try (Connection connection = manager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ProductSqlQuery.DELETE_PRODUCT.getQuery())) {
            preparedStatement.setInt(1, id);

            rowDeleted = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DaoException(e);
        }
        return rowDeleted;
    }

    private List<Product> parseCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        final List<ProductCategory> categoryList = new ArrayList<>();
        final Map<Integer, Product> map = new HashMap<>();
        int previousId = -1;

        while (resultSet.next()) {
            Integer currentId = resultSet.getInt("id");
            Product product = map.get(currentId);
            if (Optional.ofNullable(product).isEmpty()) {
                Optional.ofNullable(map.get(previousId)).ifPresent(prod -> prod.setCategoryList(new ArrayList<>(categoryList)));
                categoryList.clear();
                product = Product.builder()
                        .id(currentId)
                        .name(resultSet.getString("name"))
                        .price(resultSet.getBigDecimal("price"))
                        .quantity(resultSet.getInt("quantity"))
                        .build();
            }
            categoryList.add(ProductCategory.builder()
                    .id(resultSet.getInt("category_id"))
                    .name(resultSet.getString("category_name"))
                    .build());
            map.put(currentId, product);
            previousId = resultSet.getInt("id");
        }
        Optional.ofNullable(map.get(previousId)).ifPresent(prod -> prod.setCategoryList(new ArrayList<>(categoryList)));
        return map.values().stream().toList();
    }
}
