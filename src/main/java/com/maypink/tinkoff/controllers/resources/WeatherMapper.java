package com.maypink.tinkoff.controllers.resources;

import com.maypink.tinkoff.dto.WeatherDtoDB;
import com.maypink.tinkoff.dto.WeatherDtoWeatherApi;
import org.springframework.stereotype.Component;

@Component
public class WeatherMapper {
    public WeatherResource toResource(WeatherDtoWeatherApi weatherDtoWeatherApi) {
        return new WeatherResource(weatherDtoWeatherApi.location().name(),
                weatherDtoWeatherApi.location().region(),
                weatherDtoWeatherApi.location().country(),
                weatherDtoWeatherApi.current().tempC(),
                weatherDtoWeatherApi.current().tempF()
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
