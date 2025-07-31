create TABLE addresses (
  id BIGINT NOT NULL AUTO_INCREMENT,
  city VARCHAR(255) NOT NULL,
  street VARCHAR(255) NOT NULL,
  user_id BIGINT,
  PRIMARY KEY (id),
  KEY FK_users_addresses (user_id),
  CONSTRAINT FK_users_addresses FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);