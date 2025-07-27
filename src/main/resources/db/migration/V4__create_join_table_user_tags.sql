create TABLE user_tags (
  user_id bigint NOT NULL,
  tag_id bigint NOT NULL,
  PRIMARY KEY (user_id,tag_id),
  KEY FK_tags_user_tags (tag_id),
  CONSTRAINT FK_tags_user_tags FOREIGN KEY (tag_id) REFERENCES tags (id) ON delete CASCADE,
  CONSTRAINT FK_users_user_tags FOREIGN KEY (user_id) REFERENCES users (id) ON delete CASCADE
);
