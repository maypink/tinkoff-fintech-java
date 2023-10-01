package com.maypink.tinkoff.util;

import com.maypink.tinkoff.models.Weather;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class WeatherValidator implements Validator {
    public void validateStringParams(String regionName, Errors errors){
        this.validateStringParams(regionName, "0", String.valueOf(LocalDate.now()), errors);
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

    @Override
    public boolean supports(Class<?> clazz) {
        return Weather.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // implemented more just for a fact, there is no usage of this method in the project
        Weather weather = (Weather) target;
        this.validateStringParams(
                weather.getRegionName(), String.valueOf(weather.getTemperature()),
                String.valueOf(weather.getDate()), errors);
    }
}
