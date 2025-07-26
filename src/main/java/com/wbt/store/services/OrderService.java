package com.wbt.store.services;

import java.util.List;

public interface OrderService {
    void placeOrder(List<Long> productIds);
}
