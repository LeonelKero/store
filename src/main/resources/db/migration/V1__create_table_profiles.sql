create TABLE profiles (
  id bigint NOT NULL AUTO_INCREMENT,
  bio varchar(255) DEFAULT NULL,
  phone_number varchar(255) DEFAULT NULL,
  date_of_birth date NOT NULL,
  loyalty_points int UNSIGNED DEFAULT 0,
  PRIMARY KEY (id)
);