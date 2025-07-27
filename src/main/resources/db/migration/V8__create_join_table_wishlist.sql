create table wishlist (
    user_id bigint not null,
    product_id bigint not null,
    primary key(user_id, product_id),
    key FK_products_user_wishlist (product_id),
    constraint FK_user_wishlist foreign key (user_id) references users(id) on delete cascade,
    constraint FK_product_wishlist foreign key (product_id) references products(id) on delete cascade
);