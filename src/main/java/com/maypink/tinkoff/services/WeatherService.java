package com.maypink.tinkoff.services;

import com.maypink.tinkoff.controllers.resources.WeatherResource;
import com.maypink.tinkoff.dto.WeatherDtoWeatherApi;

import java.util.List;


public interface WeatherService {

    List<WeatherResource> getAllWeathers();
    List<WeatherResource> getWeatherByName(String regionName);
    WeatherResource add(WeatherResource weather);
    WeatherDtoWeatherApi getWeather(String key);
}
