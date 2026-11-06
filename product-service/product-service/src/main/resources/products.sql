CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL
);

INSERT IGNORE INTO products (id, name, price, quantity) VALUES (1, 'Laptop', 999.99, 30);
INSERT IGNORE INTO products (id, name, price, quantity) VALUES (2, 'Mouse', 29.99, 50);
INSERT IGNORE INTO products (id, name, price, quantity) VALUES (3, 'Keyboard', 49.99, 30);
INSERT IGNORE INTO products (id, name, price, quantity) VALUES (4, 'Monitor', 299.99, 15);
INSERT IGNORE INTO products (id, name, price, quantity) VALUES (5, 'Headphones', 79.99, 25);
UPDATE products
SET quantity = 50
WHERE name = 'Laptop';