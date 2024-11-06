-- V7__create_purchase_item_table.sql
CREATE TABLE IF NOT EXISTS purchase_item
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    purchase_id     BIGINT NOT NULL,
    product_id   BIGINT NOT NULL,
    quantity       INTEGER NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (purchase_id) REFERENCES purchase (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id)

);
