package com.maypink.tinkoff.services;

import com.maypink.tinkoff.controllers.resources.WeatherResource;
import com.maypink.tinkoff.repositories.WeatherRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class WeatherHandlingService {

    private final WeatherRepository weatherRepository;

    public WeatherHandlingService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public double getAverageTemperatureForWeatherName(String name){
        List<WeatherResource> allWeathersWithName = weatherRepository.existsByName(name);
        if (allWeathersWithName.isEmpty()){
            return 0;
        }
        return allWeathersWithName.stream()
                .limit(30)
                .sorted(Comparator.comparing( WeatherResource::dateTime ).reversed())
                .flatMapToInt(weather-> IntStream.of(weather.tempC()))
                .average()
                .orElse(0);

    }
}
