package com.maypink.tinkoff.config;

import com.maypink.tinkoff.exception.WeatherExceptionHandler;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;


@OpenAPIDefinition(
        info = @Info(
                title = "Weather App",
                description = "Homework #3, Spring Boot",
                version = "0.0.1")
)
@RequiredArgsConstructor
@Configuration
public class AppConfig {

    private final WeatherConfigProperties weatherConfigProperties;

    @Bean
    public RestTemplate openApiWeatherClient() {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(weatherConfigProperties.getHost())
                .build();
        WeatherExceptionHandler weatherExceptionHandler = new WeatherExceptionHandler(restTemplate.getMessageConverters());
        restTemplate.setErrorHandler(weatherExceptionHandler);

        return restTemplate;
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
    }
}
