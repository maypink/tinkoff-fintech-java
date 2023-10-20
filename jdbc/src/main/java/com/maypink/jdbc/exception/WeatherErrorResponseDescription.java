package com.maypink.jdbc.exception;

public record WeatherErrorResponseDescription(
        String code,
        String message
) {
}