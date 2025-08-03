package com.wbt.store.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiError(
        String status,
        String path,
        String message,
        LocalDateTime timestamp,
        Map<String, String> details
) {
}
