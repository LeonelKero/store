package com.wbt.store.services;

import java.math.BigDecimal;

public interface PaymentService {
    void proceedPayment(BigDecimal amount);
}
