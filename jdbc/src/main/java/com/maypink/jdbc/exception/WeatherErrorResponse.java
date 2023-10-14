package com.maypink.jdbc.exception;

public record WeatherErrorResponse (
        WeatherErrorResponseDescription error
) {

}