package com.maypink.tinkoff.dto;

public record WeatherDto(
        LocationDto location,
        CurrentDto current
) {
}
