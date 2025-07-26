package com.wbt.store.services.impl;

import com.wbt.store.services.NotificationService;
import org.springframework.stereotype.Service;

@Service("sms")
public class SmsNotification implements NotificationService {
    @Override
    public void send(String message, String destination) {
        System.out.println("SMS - Sending notification " + message + " to " + destination);
    }
}
