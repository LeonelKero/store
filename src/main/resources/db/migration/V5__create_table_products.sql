create TABLE products (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  price DECIMAL(19,2) NOT NULL DEFAULT 0.0,
  category_id TINYINT UNSIGNED,
  PRIMARY KEY (id),
  FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE RESTRICT
);
