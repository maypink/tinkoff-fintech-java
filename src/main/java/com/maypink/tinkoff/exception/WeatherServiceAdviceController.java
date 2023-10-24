package com.maypink.tinkoff.exception;

import com.maypink.tinkoff.exception.customException.WeatherDuplicateException;
import com.maypink.tinkoff.exception.customException.WeatherException;
import com.maypink.tinkoff.exception.customException.WeatherNotFoundException;
import com.maypink.tinkoff.exception.customException.WeatherTransactionException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WeatherServiceAdviceController {

    @ExceptionHandler(value = ResponseWeatherErrorException.class)
    public ResponseEntity<ErrorResponse> getErrorResponseForWeatherApi(ResponseWeatherErrorException ex, HttpServletRequest request) {
        ResponseEntity<WeatherErrorResponse> weatherErrorResponse = ex.getWeatherErrorResponse();
        return new ResponseEntity<>(new ErrorResponse(weatherErrorResponse.getStatusCode().value(), weatherErrorResponse.getBody().error().message(), weatherErrorResponse.getBody().error().code(), request.getRequestURI()), weatherErrorResponse.getStatusCode());
    }

    @ExceptionHandler(value = RequestNotPermitted.class)
    public ResponseEntity<ErrorResponse> getErrorResponseForRateLimiter(RequestNotPermitted ex, HttpServletRequest request) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.TOO_MANY_REQUESTS.value(), ex.getMessage(), "Too many requests to server", request.getRequestURI()), HttpStatus.TOO_MANY_REQUESTS);
    }
    @ExceptionHandler(value = WeatherNotFoundException.class)
    public ResponseEntity<ErrorResponse> getErrorResponseForWeatherApi(WeatherNotFoundException ex, HttpServletRequest request) {
        return handleException(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = WeatherDuplicateException.class)
    public ResponseEntity<ErrorResponse> getErrorResponseForWeatherApi(WeatherDuplicateException ex, HttpServletRequest request) {
        return handleException(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = WeatherTransactionException.class)
    public ResponseEntity<ErrorResponse> getErrorResponseForWeatherApi(WeatherTransactionException ex, HttpServletRequest request) {
        return handleException(ex, request, HttpStatus.BAD_REQUEST);
    }


    private ResponseEntity<ErrorResponse> handleException(WeatherException ex, HttpServletRequest request, HttpStatusCode httpStatusCode){
        String message = ex.getMessage();
        return new ResponseEntity<>(new ErrorResponse(httpStatusCode.value(), httpStatusCode.toString(), message, request.getRequestURI()), httpStatusCode);
    }

}
