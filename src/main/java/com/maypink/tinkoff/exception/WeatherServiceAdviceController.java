package com.maypink.tinkoff.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;

@ControllerAdvice
public class WeatherServiceAdviceController {

    @ExceptionHandler(value = HttpStatusCodeException.class)
    public ResponseEntity<ErrorResponse> getErrorResponse(HttpStatusCodeException ex, HttpServletRequest request) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), ex.getStatusCode().value(), ex.getStatusCode().toString(), request.getRequestURI()), ex.getStatusCode());
    }
}
