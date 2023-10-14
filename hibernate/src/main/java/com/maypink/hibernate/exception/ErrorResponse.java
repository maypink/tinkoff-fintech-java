package com.maypink.hibernate.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ErrorResponse(
        String timestamp,
        Integer status,
        String error,
        String message,
        String path
) {

    public ErrorResponse(String message, Integer status, String error, String path) {
        this(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()), status, error, message, path);
    }
}