DROP TABLE IF EXISTS products_and_categories;
DROP TABLE IF EXISTS product_category;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS order_detail;


CREATE TABLE order_detail
(
    id           BIGSERIAL   NOT NULL,
    order_status varchar(20) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE product
(
    id       BIGSERIAL NOT NULL,
    order_id INT,
    price    INT,
    name     VARCHAR(255),
    quantity INT,
    FOREIGN KEY (order_id) REFERENCES order_detail (id),
    PRIMARY KEY (id)
);

CREATE TABLE product_category
(
    id       BIGSERIAL NOT NULL,
    name     VARCHAR(255),
    type     VARCHAR(100),
    PRIMARY KEY (id)
);

CREATE TABLE products_and_categories
(
    product_id int,
    category_id int,
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (category_id) REFERENCES product_category (id),
    PRIMARY KEY (product_id, category_id)
);
