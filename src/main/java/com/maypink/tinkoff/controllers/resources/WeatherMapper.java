package com.maypink.tinkoff.controllers.resources;

import com.maypink.tinkoff.dto.WeatherDto;
import org.springframework.stereotype.Component;

@Component
public class WeatherMapper {
    public WeatherResource toResource(WeatherDto weatherDto) {
        return new WeatherResource(weatherDto.location().name(),
                weatherDto.location().region(),
                weatherDto.location().country(),
                weatherDto.location().localtime(),
                weatherDto.current().tempC(),
                weatherDto.current().tempF()
        );
    }
}
