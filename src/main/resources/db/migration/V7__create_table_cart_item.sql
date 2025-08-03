CREATE TABLE carts (
    id BINARY(16) PRIMARY KEY,  -- Stores UUID as 16-byte binary
    created_date DATETIME NOT NULL
);

CREATE TABLE cart_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    cart_id BINARY(16) NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT, -- Prevent product deletion when it is in someone cart
    UNIQUE KEY UK_cart_product (cart_id, product_id)  -- Prevent duplicates
);
