package com.wbt.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationManager {
    private final NotificationService notificationService;

    void messageCustomer(final String message) {
        this.notificationService.send(message, "leonel kan");
    }
}
