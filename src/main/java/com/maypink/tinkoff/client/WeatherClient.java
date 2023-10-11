package com.maypink.tinkoff.client;

import com.maypink.tinkoff.config.WeatherConfigProperties;
import com.maypink.tinkoff.dto.WeatherDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherClient {

    private final WeatherConfigProperties weatherConfigProperties;

    private final RestTemplate restTemplate;

    public WeatherClient(@Qualifier(value = "openApiWeatherClient") RestTemplate restTemplate, WeatherConfigProperties weatherConfigProperties) {
        this.restTemplate = restTemplate;
        this.weatherConfigProperties = weatherConfigProperties;
    }

    public WeatherDto getWeather(String query) {
        return restTemplate.getForObject(weatherConfigProperties.getEndpoint() + "?q=" +
                query + "&key=" + weatherConfigProperties.getApiKey(), WeatherDto.class);
    }
}
