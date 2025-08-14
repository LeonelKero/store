CREATE TABLE profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bio TEXT,
    phone_number VARCHAR(20),
    date_of_birth DATE NOT NULL,
    loyalty_points INT,
    user_id BIGINT UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);