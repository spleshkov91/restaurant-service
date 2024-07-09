package com.pleshkov.dao.sql_query;

import lombok.Getter;

@Getter
public enum ProductSqlQuery {
    INSERT_PRODUCT("""
            INSERT INTO product (name, price, quantity)
            VALUES (?, ?, ?)
            """),
    UPDATE_PRODUCT("""
    UPDATE product
    SET name=?, price=?, quantity=?
    WHERE id=?
    """),
    SELECT_PRODUCT_BY_ID("""
            SELECT p.id, p.name, p.price, p.quantity, p_a_c.category_id, p_c.name as category_name
            FROM product as p
            LEFT JOIN products_and_categories as p_a_c
            ON p.id = p_a_c.product_id
            LEFT JOIN product_category as p_c
            ON p_a_c.category_id = p_c.id
            WHERE p.id=?
            """),
    SELECT_ALL_PRODUCTS("""
            SELECT p.id, p.name, p.price, p.quantity, p_a_c.category_id, p_c.name as category_name
            FROM product as p
            LEFT JOIN products_and_categories as p_a_c
            ON p.id = p_a_c.product_id
            LEFT JOIN product_category as p_c
            ON p_a_c.category_id = p_c.id
            ORDER BY p.name
            """),
    DELETE_PRODUCT("""
            DELETE
            FROM product
            WHERE id=?
            """);

    public final String query;

    ProductSqlQuery(String query) {
        this.query = query;
    }
}
