package com.maypink.tinkoff.unit;

import com.maypink.tinkoff.cache.WeatherCache;
import com.maypink.tinkoff.client.WeatherClient;
import com.maypink.tinkoff.controllers.resources.WeatherResource;
import com.maypink.tinkoff.repositories.WeatherRepository;
import com.maypink.tinkoff.services.WeatherService;
import com.maypink.tinkoff.services.WeatherServiceImpl;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;

public class WeatherServiceImplTests {

    private final WeatherCache weatherCache = Mockito.mock(WeatherCache.class);

    private final WeatherClient weatherClient = Mockito.mock(WeatherClient.class);

    private final WeatherRepository weatherRepository = Mockito.mock(WeatherRepository.class);

    private final WeatherService weatherServiceImpl = new WeatherServiceImpl(weatherRepository, weatherClient, weatherCache);


    @Test
    public void getWeather_WhenItIsInCache_ThenGoToCache(){
        WeatherResource weatherResource = new WeatherResource(
                "weather",
                "region",
                "country",
                0,
                0D);
        String weatherName = weatherResource.name();
        // set conditions
        Mockito.when(weatherCache.get(weatherName)).thenReturn(Optional.of(weatherResource));
        Mockito.when(weatherRepository.existsByName(weatherName)).thenReturn(List.of());
        // check
        assertEquals(weatherServiceImpl.getWeatherByName(weatherName).get(0), weatherResource);
        Mockito.verify(weatherRepository, times(0)).existsByName(weatherName);
        Mockito.verify(weatherCache, times(1)).get(weatherName);
    }

    @Test
    public void getWeather_WhenItIsNotInCache_ThenGoToDataBase(){
        WeatherResource weatherResource = new WeatherResource(
                "weather",
                "region",
                "country",
                0,
                0D);
        String weatherName = weatherResource.name();
        // set conditions
        Mockito.when(weatherCache.get(weatherName)).thenReturn(Optional.empty());
        Mockito.when(weatherRepository.existsByName(weatherName)).thenReturn(List.of(weatherResource));
        // check
        assertEquals(weatherServiceImpl.getWeatherByName(weatherName).get(0), weatherResource);
        Mockito.verify(weatherRepository, times(1)).existsByName(weatherName);
        // since we will check cache anyway
        Mockito.verify(weatherCache, times(1)).get(weatherName);
    }

    @Test
    public void updateWeather_WeatherInCacheIsUpdatedToo() throws SQLException {
        WeatherResource weatherResource = new WeatherResource(
                "weather",
                "region",
                "country",
                0,
                0D);
        WeatherResource weatherResourceUpdated = new WeatherResource(
                "weather",
                "regionNew",
                "country",
                100,
                0D);
        String weatherName = weatherResource.name();
        Mockito.when(weatherCache.get(weatherName)).thenReturn(Optional.of(weatherResource));
        weatherServiceImpl.add(weatherResourceUpdated);
        Mockito.verify(weatherCache, times(1)).put(weatherResourceUpdated.name(), weatherResourceUpdated);
        Mockito.verify(weatherRepository, times(1)).updateWeather(weatherResourceUpdated);
    }
}