package com.maypink.tinkoff.services;

import com.maypink.tinkoff.models.Weather;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface WeatherService {
    List<Weather> getWeatherByRegionNameAndDate(String regionName, LocalDate date);
    Weather add(Weather weather);
    Weather update(Weather weather);
    Optional<List<Weather>> deleteByRegionName(String regionName);
}
