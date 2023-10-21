package com.maypink.tinkoff.exception.customException;

public class WeatherNotFoundException extends WeatherException{
    public WeatherNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage(){
        return message;
    }
}
