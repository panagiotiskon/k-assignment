-- V4__alter_product_table.sql
ALTER TABLE product
    ADD COLUMN company
        VARCHAR(256) AFTER price;

ALTER TABLE product
    ADD COLUMN created_date
        TIMESTAMP DEFAULT CURRENT_TIMESTAMP AFTER company;

ALTER TABLE product
    ADD COLUMN updated_date
        TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP AFTER created_date;
