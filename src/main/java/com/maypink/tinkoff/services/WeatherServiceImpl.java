package com.maypink.tinkoff.services;

import com.maypink.tinkoff.cache.WeatherCache;
import com.maypink.tinkoff.client.WeatherClient;
import com.maypink.tinkoff.controllers.resources.WeatherResource;
import com.maypink.tinkoff.dto.WeatherApiResponse;
import com.maypink.tinkoff.exception.customException.WeatherDuplicateException;
import com.maypink.tinkoff.exception.customException.WeatherException;
import com.maypink.tinkoff.repositories.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class WeatherServiceImpl implements WeatherService{
    @Autowired
    private final WeatherRepository weatherRepository;

    @Autowired
    private final WeatherClient weatherClient;

    @Autowired
    private final WeatherCache weatherCache;

    public WeatherServiceImpl(WeatherRepository weatherRepository, WeatherClient weatherClient, WeatherCache weatherCache){
        this.weatherRepository = weatherRepository;
        this.weatherClient = weatherClient;
        this.weatherCache = weatherCache;
    }

    @Override
    public List<WeatherResource> getAllWeathers(){
        return weatherRepository.getAllWeathers();
    }


    @Override
    public List<WeatherResource> getWeatherByName(String regionName) throws WeatherException {
        Optional<WeatherResource> weatherResource = weatherCache.get(regionName);
        if (weatherResource.isPresent()){
            return weatherResource.stream().toList();
        } else {
        return weatherRepository.existsByName(regionName);

        }
    }

    @Override
    public WeatherResource add(WeatherResource weatherResource) throws WeatherException {
        List<WeatherResource> weathers = getWeatherByName(weatherResource.name());
        if (weathers.isEmpty()) {
            weatherCache.put(weatherResource.name(), weatherResource);
            weatherRepository.addWeather(weatherResource);
            return weatherResource;
        } else {
            // if new WeatherResource is the same as it was
            if (compareWeatherResources(weatherResource, weathers.get(0))){
                throw new WeatherDuplicateException("Attempt to insert duplicate of Weather.");
            } else {
                // if something has changed
                weatherCache.put(weatherResource.name(), weatherResource);
                weatherRepository.updateWeather(weatherResource);
                return weatherResource;
            }
        }
    }

    @Override
    public WeatherApiResponse getWeather(String key){
        return weatherClient.getWeather(key);
    }

    private boolean compareWeatherResources(WeatherResource weatherResourceNew, WeatherResource weatherResourceOld){
        return weatherResourceNew == weatherResourceOld;
    }
}
