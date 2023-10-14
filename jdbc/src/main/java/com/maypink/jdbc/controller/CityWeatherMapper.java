package com.maypink.jdbc.controller;


import com.maypink.jdbc.dto.CityWeatherDto;
import com.maypink.jdbc.model.CityWeather;
import org.springframework.stereotype.Component;

@Component
public class CityWeatherMapper {
    public CityWeatherDto toDto(CityWeather cityWeather) {
        return new CityWeatherDto(
                cityWeather.getId(),
                cityWeather.getCity().getName(),
                cityWeather.getWeatherType().getType()
        );
    }
}
