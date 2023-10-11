package com.maypink.tinkoff.exception;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WeatherServiceAdviceController {

    @ExceptionHandler(value = ResponseWeatherErrorException.class)
    public ResponseEntity<ErrorResponse> getErrorResponseForWeatherApi(ResponseWeatherErrorException ex, HttpServletRequest request) {
        ResponseEntity<WeatherErrorResponse> weatherErrorResponse = ex.getWeatherErrorResponse();
        return new ResponseEntity<>(new ErrorResponse(weatherErrorResponse.getBody().error().message(), weatherErrorResponse.getStatusCode().value(), weatherErrorResponse.getBody().error().code(), request.getRequestURI()), weatherErrorResponse.getStatusCode());
    }

    @ExceptionHandler(value = RequestNotPermitted.class)
    public ResponseEntity<ErrorResponse> getErrorResponseForRateLimiter(RequestNotPermitted ex, HttpServletRequest request) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), HttpStatus.TOO_MANY_REQUESTS.value(), "Too many requests to server", request.getRequestURI()), HttpStatus.TOO_MANY_REQUESTS);
    }

}
