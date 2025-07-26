package com.wbt.store.services.impl;

import com.wbt.store.services.PaymentService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("paypal")
@Primary
public class PaypalPayment implements PaymentService {
    @Override
    public void proceedPayment(BigDecimal amount) {
        System.out.println("PAYPAL payment of $" + amount);
    }
}
