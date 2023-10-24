package com.maypink.tinkoff.exception.customException;

public class WeatherTransactionException extends WeatherException{
    public WeatherTransactionException(String message) {
        super(message);
    }

    @Override
    public String getMessage(){
        return message;
    }
}
