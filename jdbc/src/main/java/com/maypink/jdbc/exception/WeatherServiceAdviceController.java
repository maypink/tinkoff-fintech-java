package com.maypink.jdbc.exception;

import com.maypink.jdbc.exception.customException.WeatherDuplicateException;
import com.maypink.jdbc.exception.customException.WeatherException;
import com.maypink.jdbc.exception.customException.WeatherNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WeatherServiceAdviceController {

    @ExceptionHandler(value = WeatherNotFoundException.class)
    public ResponseEntity<ErrorResponse> getErrorResponseForWeatherApi(WeatherNotFoundException ex, HttpServletRequest request) {
        return handleException(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = WeatherDuplicateException.class)
    public ResponseEntity<ErrorResponse> getErrorResponseForWeatherApi(WeatherDuplicateException ex, HttpServletRequest request) {
        return handleException(ex, request, HttpStatus.CONFLICT);
    }

    private ResponseEntity<ErrorResponse> handleException(WeatherException ex, HttpServletRequest request, HttpStatusCode httpStatusCode){
        String message = ex.getMessage();
        return new ResponseEntity<>(new ErrorResponse(httpStatusCode.value(), httpStatusCode.toString(), message, request.getRequestURI()), httpStatusCode);
    }

}