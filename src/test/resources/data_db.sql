INSERT INTO order_detail (order_status)
VALUES ('READY'),
       ('READY'),
       ('READY');

INSERT INTO product (order_id, price, name, quantity)
VALUES (1, 200, 'test tea', 5),
       (1, 300, 'fresh coctail', 3),
       (2, 150, 'coffe', 7),
       (2, 250, 'beer', 3),
       (3, 250, 'vodka', 3);


INSERT INTO product_category (name, type)
VALUES ('cold', 'drink'),
       ('hot', 'drink'),
       ('alcohol', 'drink'),
       ('non-alcohol','drink');

INSERT INTO products_and_categories (product_id, category_id)
VALUES (1, 1),
       (2, 1),
       (3, 2),
       (4, 3),
       (5, 3),
       (1, 4),
       (2, 4),
       (3, 4);