package com.wbt.store.services.impl;

import com.wbt.store.services.NotificationService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("email")
@Primary
public class EmailNotification implements NotificationService {
    @Override
    public void send(String message, String destination) {
        System.out.println("EMAIL - Sending notification " + message + " to " + destination);
    }
}
