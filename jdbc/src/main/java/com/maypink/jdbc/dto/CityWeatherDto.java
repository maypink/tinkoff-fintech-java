package com.maypink.jdbc.dto;

public record CityWeatherDto(
        Long id,
        String cityName,
        String weatherType
) {}
