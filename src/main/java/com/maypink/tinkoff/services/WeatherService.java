package com.maypink.tinkoff.services;

import com.maypink.tinkoff.models.Weather;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface WeatherService {
    List<Weather> getWeatherByRegionNameAndDate(String regionName, LocalDate date);
    Weather add(String regionName, Integer temperature, LocalDate date);
    Weather update(String regionName, Integer temperature, LocalDate date);
    Optional<List<Weather>> deleteByRegionName(String regionName);
}
