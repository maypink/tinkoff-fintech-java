package com.maypink.tinkoff.exception;

public record WeatherErrorResponseDescription(
        String code,
        String message
) {
}
