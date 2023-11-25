package com.maypink.tinkoff.services;

import com.maypink.tinkoff.controllers.resources.WeatherMapper;
import com.maypink.tinkoff.controllers.resources.WeatherResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WeatherProducer {

    private final WeatherMapper weatherMapper;

    private final WeatherProvider weatherProvider;

    private final KafkaTemplate<String, WeatherResource> kafkaTemplate;

    private final WeatherService weatherService;

    @Autowired
    public WeatherProducer(
            WeatherMapper weatherMapper, final WeatherProvider weatherProvider,
            final KafkaTemplate<String, WeatherResource> kafkaTemplate,
            WeatherService weatherService) {
        this.weatherMapper = weatherMapper;
        this.weatherProvider = weatherProvider;
        this.kafkaTemplate = kafkaTemplate;
        this.weatherService = weatherService;
    }

    @Scheduled(cron = "${weather.frequency}")
    private void publish() {

        final String location = weatherProvider.getWeatherName();

        final var weather = weatherMapper.toResource(
                weatherService.getWeather(location
                )
        );

        sendWeather(weather);
    }

    private void sendWeather(final WeatherResource weather) {
        final String weatherName = weather.name();
        kafkaTemplate.sendDefault(weatherName, weather);
        log.info("Weather for city {} was sent", weatherName);
    }
}
