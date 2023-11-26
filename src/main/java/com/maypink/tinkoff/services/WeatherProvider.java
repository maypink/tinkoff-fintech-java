package com.maypink.tinkoff.services;

import com.maypink.tinkoff.config.CitiesListConfig;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.stream.Collectors;

@Validated
@Component
public class WeatherProvider {

    @Size(min = 1)
    private final Deque<String> deque;


    public WeatherProvider(final CitiesListConfig citiesConfigParams) {
        this.deque = Arrays.stream(citiesConfigParams.getCities())
                .distinct()
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    public String getWeatherName() {
        final String city = this.deque.poll();
        this.deque.addLast(city);
        return city;
    }
}
