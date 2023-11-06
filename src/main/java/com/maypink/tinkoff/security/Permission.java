package com.maypink.tinkoff.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum Permission {

    WEATHER_READ("cities:read"),

    WEATHER_WRITE("cities:write");

    private final String descriptor;
}