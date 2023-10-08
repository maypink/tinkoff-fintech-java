package com.maypink.tinkoff.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WeatherServiceAdviceController {

    @ExceptionHandler(value = ResponseWeatherErrorException.class)
    public ResponseEntity<ErrorResponse> getErrorResponse(ResponseWeatherErrorException ex, HttpServletRequest request) {
        ResponseEntity<WeatherErrorResponse> weatherErrorResponse = ex.getWeatherErrorResponse();
        return new ResponseEntity<>(new ErrorResponse(weatherErrorResponse.getBody().error().message(), weatherErrorResponse.getStatusCode().value(), weatherErrorResponse.getBody().error().code(), request.getRequestURI()), weatherErrorResponse.getStatusCode());
    }
}
