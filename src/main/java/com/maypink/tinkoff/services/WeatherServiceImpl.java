package com.maypink.tinkoff.services;

import com.maypink.tinkoff.models.Weather;
import com.maypink.tinkoff.repositories.WeatherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class WeatherServiceImpl implements WeatherService{
    private final WeatherRepository weatherRepository;

    public WeatherServiceImpl(WeatherRepository weatherRepository){
        this.weatherRepository = weatherRepository;
    }


    public List<Weather> getWeatherByRegionNameAndDate(String regionName, LocalDate date) {
        return weatherRepository.getWeatherByRegionAndDate(regionName, date);
    }

    @Override
    public Weather add(String regionName, Integer temperature, LocalDate date) {
        return weatherRepository.addWeather(regionName, temperature, date);
    }

    @Override
    public Weather update(String regionName, Integer temperature, LocalDate date) {
        return weatherRepository.updateWeather(regionName, temperature, date);
    }

    @Override
    public Optional<List<Weather>> delete(String regionName, LocalDate date) {
        return weatherRepository.deleteWeather(regionName);
    }
}
