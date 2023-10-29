package com.maypink.tinkoff.services;

import com.maypink.tinkoff.client.WeatherClient;
import com.maypink.tinkoff.controllers.resources.WeatherResource;
import com.maypink.tinkoff.dto.WeatherApiResponse;
import com.maypink.tinkoff.exception.customException.WeatherDuplicateException;
import com.maypink.tinkoff.exception.customException.WeatherException;
import com.maypink.tinkoff.repositories.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WeatherServiceImpl implements WeatherService{
    @Autowired
    private final WeatherRepository weatherRepository;

    @Autowired
    private final WeatherClient weatherClient;

    public WeatherServiceImpl(WeatherRepository weatherRepository, WeatherClient weatherClient){
        this.weatherRepository = weatherRepository;
        this.weatherClient = weatherClient;
    }

    @Override
    public List<WeatherResource> getAllWeathers(){
        return weatherRepository.getAllWeathers();
    }


    @Override
    public List<WeatherResource> getWeatherByName(String regionName) throws WeatherException {
        return weatherRepository.existsByName(regionName);
    }

    @Override
    public WeatherResource add(WeatherResource weatherResource) throws WeatherException {
        List<WeatherResource> weathers = getWeatherByName(weatherResource.name());
        if (weathers.isEmpty()) {
            weatherRepository.addWeather(weatherResource);
            return weatherResource;
        } else {
            throw new WeatherDuplicateException("Attempt to insert duplicate of Weather.");
        }
    }

    @Override
    public WeatherApiResponse getWeather(String key){
        return weatherClient.getWeather(key);
    }
}
