-- V1_create_user_table.sql
CREATE TABLE IF NOT EXISTS user
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    email        VARCHAR(128) UNIQUE NOT NULL,
    password     VARCHAR(128)        NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)

engine = innodb
DEFAULT charset = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;