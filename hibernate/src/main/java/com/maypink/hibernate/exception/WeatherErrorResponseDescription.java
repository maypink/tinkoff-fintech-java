package com.maypink.hibernate.exception;

public record WeatherErrorResponseDescription(
        String code,
        String message
) {
}