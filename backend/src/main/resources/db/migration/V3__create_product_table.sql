-- V3_create_product_table.sql
CREATE TABLE IF NOT EXISTS product
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    sku         BIGINT       NOT NULL UNIQUE,
    name        VARCHAR(128) NOT NULL,
    description VARCHAR(256),
    price       DOUBLE       NOT NULL
);