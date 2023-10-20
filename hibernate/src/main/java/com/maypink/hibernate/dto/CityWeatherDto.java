package com.maypink.hibernate.dto;

public record CityWeatherDto(
        Long id,
        String cityName,
        String weatherType
) {}
