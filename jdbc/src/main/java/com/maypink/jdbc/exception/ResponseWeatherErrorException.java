package com.maypink.jdbc.exception;

import org.springframework.http.ResponseEntity;

public class ResponseWeatherErrorException extends RuntimeException{
    private final ResponseEntity<WeatherErrorResponse> weatherErrorResponse;

    public ResponseWeatherErrorException(ResponseEntity<WeatherErrorResponse> weatherErrorResponse) {
        this.weatherErrorResponse = weatherErrorResponse;
    }

    public ResponseEntity<WeatherErrorResponse> getWeatherErrorResponse() {
        return weatherErrorResponse;
    }
}
