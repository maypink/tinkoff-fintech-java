package com.maypink.tinkoff.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@OpenAPIDefinition(
        info = @Info(
                title = "Weather App",
                description = "Homework #3, Spring Boot",
                version = "0.0.1")
)
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate openApiWeatherClient() {
        return new RestTemplateBuilder().rootUri("http://api.weatherapi.com").build();
    }
}
