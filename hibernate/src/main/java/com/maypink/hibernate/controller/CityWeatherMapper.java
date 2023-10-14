package com.maypink.hibernate.controller;


import com.maypink.hibernate.dto.CityWeatherDto;
import com.maypink.hibernate.model.CityWeather;
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
