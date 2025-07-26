package com.wbt.store.services.impl;

import com.wbt.store.services.PaymentService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("stripe")
public class StripePayment implements PaymentService {

    @Override
    public void proceedPayment(BigDecimal amount) {
        System.out.println("STRIPE payment of $" + amount);
    }
}
