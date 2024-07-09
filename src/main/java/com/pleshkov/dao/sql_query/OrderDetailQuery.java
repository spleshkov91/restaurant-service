package com.pleshkov.dao.sql_query;

import lombok.Getter;

@Getter
public enum OrderDetailQuery {
    SELECT_ALL_ORDERS("""
            SELECT p.id, p.name, p.price, p.quantity, o.order_id, o.order_status
            FROM product as p
            LEFT JOIN order_detail as o
            ON p.order_id = o.order_id"""),
    TOTAL_AMOUNT_ORDER("""
            SELECT o.order_id, sum(case when p.order_id = o.order_id then p.price end )
            FROM product as p
            LEFT JOIN order_detail as o
            ON o.order_id = p.order_id
            WHERE o.order_id = ?
            GROUP BY o.order_id"""),
    SELECT_ORDER_BY_ID("""
            SELECT p.id, p.name, p.price, p.quantity, o.order_id, o.order_status
            FROM product as p
            LEFT JOIN order_detail as o
            ON p.order_id = o.order_id
            WHERE p.order_id = ?
            """),
    DELETE_ORDER("""
            DELETE
            FROM order_detail
            WHERE id=?
            """);


    public final String query;

    OrderDetailQuery(String query) {
        this.query = query;
    }
}
