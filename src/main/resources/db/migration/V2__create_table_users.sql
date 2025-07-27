create TABLE users (
  id bigint NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  profile_id bigint DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_6dotkott2kjsp8vw4d0m25fb7 (email),
  UNIQUE KEY UK_k11y3pdtsrjgy8w9b6q4bjwrx (profile_id),
  CONSTRAINT FK_profiles_users FOREIGN KEY (profile_id) REFERENCES profiles (id)
);
