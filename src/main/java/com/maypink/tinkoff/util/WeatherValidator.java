package com.maypink.tinkoff.util;

import com.maypink.tinkoff.models.Weather;
import com.maypink.tinkoff.repositories.WeatherRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.util.List;

@Component
public class WeatherValidator {
    public void validateStringParams(String regionName, String date, Errors errors){
        this.validateStringParams(regionName, "0", date, errors);
    }
    public void validateStringParams(String regionName, String temperature, String date, Errors errors){
        try {
            Integer.parseInt(temperature);
        } catch (NumberFormatException exception){
            errors.rejectValue("temperature", "422", "Incorrect temperature");
        }
        try {
            LocalDate.parse(date);
        } catch (Exception exception){
            errors.rejectValue("date", "422", "Incorrect date");
        }
    }
}
