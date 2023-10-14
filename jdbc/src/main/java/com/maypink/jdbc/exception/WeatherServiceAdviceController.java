package com.maypink.jdbc.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WeatherServiceAdviceController {

    @ExceptionHandler(value = ResponseWeatherErrorException.class)
    public ResponseEntity<ErrorResponse> getErrorResponseForWeatherApi(ResponseWeatherErrorException ex, HttpServletRequest request) {
        ResponseEntity<WeatherErrorResponse> weatherErrorResponse = ex.getWeatherErrorResponse();
        return new ResponseEntity<>(new ErrorResponse(weatherErrorResponse.getStatusCode().toString(), weatherErrorResponse.getStatusCode().value(), weatherErrorResponse.getStatusCode().toString(), request.getRequestURI()), weatherErrorResponse.getStatusCode());
    }

}