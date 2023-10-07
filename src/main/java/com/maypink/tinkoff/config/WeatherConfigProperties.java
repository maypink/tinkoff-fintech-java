package com.maypink.tinkoff.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "weather-service")
@Getter
@Setter
public class WeatherConfigProperties {

    private String apiKey;

}