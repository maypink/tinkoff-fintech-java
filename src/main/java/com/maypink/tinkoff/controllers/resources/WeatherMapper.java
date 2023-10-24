package com.maypink.tinkoff.controllers.resources;

import com.maypink.tinkoff.dto.WeatherDtoDB;
import com.maypink.tinkoff.dto.WeatherApiResponse;
import org.springframework.stereotype.Component;

@Component
public class WeatherMapper {
    public WeatherResource toResource(WeatherApiResponse weatherApiResponse) {
        return new WeatherResource(weatherApiResponse.location().name(),
                weatherApiResponse.location().region(),
                weatherApiResponse.location().country(),
                weatherApiResponse.current().tempC(),
                weatherApiResponse.current().tempF()
        );
    }

    public WeatherResource toResource(WeatherDtoDB weatherDtoDB) {
        return new WeatherResource(weatherDtoDB.getName(),
                weatherDtoDB.getRegion(),
                weatherDtoDB.getCountry(),
                weatherDtoDB.getTempC(),
                weatherDtoDB.getTempF()
        );
    }
}
