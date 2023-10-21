package com.maypink.tinkoff.dto;

public record WeatherDtoWeatherApi(
        LocationDto location,
        CurrentDto current
) {
}
