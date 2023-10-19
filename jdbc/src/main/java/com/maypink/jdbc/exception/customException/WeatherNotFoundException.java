package com.maypink.jdbc.exception.customException;

public class WeatherNotFoundException extends WeatherException{
    public WeatherNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage(){
        return message;
    }
}
