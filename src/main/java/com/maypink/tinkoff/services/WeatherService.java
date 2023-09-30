package com.maypink.tinkoff.services;

import com.maypink.tinkoff.models.Weather;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface WeatherService {
    Weather add(String regionName, Integer temperature, LocalDate date);
    Weather update(String regionName, Integer temperature, LocalDate date);
    Optional<List<Weather>> delete(String regionName, LocalDate date);
}
