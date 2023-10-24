package com.maypink.tinkoff.dto;

public record WeatherApiResponse(
        LocationDto location,
        CurrentDto current
) {
}
