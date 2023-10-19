package com.maypink.hibernate.exception.customException;

public class WeatherException extends RuntimeException{
    String message;

    public WeatherException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
