package com.maypink.tinkoff.controllers.resources;

public record WeatherResource(
        // does not really make sense to think a lot about fields to consider, since it is not the main point
        String name,
        String region,
        String country,
        Integer tempC,
        Double tempF,
        String dateTime
) { }
