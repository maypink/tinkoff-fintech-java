package com.maypink.tinkoff.services;

import com.maypink.tinkoff.controllers.resources.WeatherResource;
import com.maypink.tinkoff.repositories.WeatherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class WeatherConsumer {

    private final WeatherService weatherAPIService;

    private final WeatherHandlingService weatherHandlingService;

    private final WeatherRepository weatherRepository;

    public WeatherConsumer(
            WeatherService weatherAPIService,
            WeatherHandlingService weatherHandlingService,
            WeatherRepository weatherRepository) {
        this.weatherAPIService = weatherAPIService;
        this.weatherHandlingService = weatherHandlingService;
        this.weatherRepository = weatherRepository;
    }

    @KafkaListener(topics = "${spring.kafka.template.default-topic}")
    private void process(final WeatherResource weather) {
        final String cityName = weather.name();
        log.info("Weather for city {} was received", cityName);

        List<WeatherResource> savedWeathers = weatherRepository.existsByNameAndDateTime(
                weather.name(),
                weather.dateTime());
        if (savedWeathers.isEmpty()){
            weatherRepository.addWeather(weather);
            log.info("Weather for {} was saved", weather.name());
        } else {
            log.info("Weather for {} was NOT saved, since for this date&time it already exists", weather.name());
        }

        Double averageTemp = weatherHandlingService.getAverageTemperatureForWeatherName(weather.name());
        log.info("Average temperature for the last 30 requests for {} is {}", weather.name(), averageTemp);
    }
}
