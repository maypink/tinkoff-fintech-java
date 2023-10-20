package com.maypink.hibernate.config;

import com.maypink.hibernate.exception.WeatherExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate openApiWeatherClient() {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        WeatherExceptionHandler weatherExceptionHandler = new WeatherExceptionHandler(restTemplate.getMessageConverters());
        restTemplate.setErrorHandler(weatherExceptionHandler);
        return restTemplate;
    }
}
