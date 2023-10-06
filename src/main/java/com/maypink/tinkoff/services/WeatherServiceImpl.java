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
    public Weather add(Weather weather) {
        return weatherRepository.addWeather(weather);
    }

    @Override
    public Weather update(Weather weather) {
        List<Weather> filteredWeathers = weatherRepository.getWeatherByRegionAndDate(weather.getRegionName(), weather.getDate());

        // if there is no such Weather object
        if (filteredWeathers.isEmpty()) {
            return weatherRepository.addWeather(weather);
        } else {
            // there must be only zero or one weather with specified date and region, since adding new object with the same data
            // is impossible
            Weather weatherToUpdate = filteredWeathers.get(0);
            return weatherRepository.updateWeatherWithTemperature(weatherToUpdate, weather.getTemperature());
        }
    }

    @Override
    public Optional<List<Weather>> deleteByRegionName(String regionName) {
        return weatherRepository.deleteWeather(regionName);
    }
}
