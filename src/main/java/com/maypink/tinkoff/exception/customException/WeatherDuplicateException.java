package com.maypink.tinkoff.exception.customException;

public class WeatherDuplicateException extends WeatherException{
    public WeatherDuplicateException(String message) {
        super(message);
    }

    @Override
    public String getMessage(){
        return message;
    }
}