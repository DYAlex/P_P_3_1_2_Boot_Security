package ru.kata.spring.boot_security.demo.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserErrorResponse {
    private String message;
    private long timestamp;

    public UserErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
