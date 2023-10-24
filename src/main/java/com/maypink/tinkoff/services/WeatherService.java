package com.maypink.tinkoff.services;

import com.maypink.tinkoff.controllers.resources.WeatherResource;
import com.maypink.tinkoff.dto.WeatherApiResponse;

import java.sql.SQLException;
import java.util.List;


public interface WeatherService {

    List<WeatherResource> getAllWeathers();
    List<WeatherResource> getWeatherByName(String regionName);
    WeatherResource addJdbc(WeatherResource weather) throws SQLException;

    WeatherResource addSpring(WeatherResource weather) throws SQLException;
    WeatherApiResponse getWeather(String key);
}
