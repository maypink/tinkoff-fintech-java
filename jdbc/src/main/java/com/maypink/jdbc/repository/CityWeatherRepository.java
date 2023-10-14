package com.maypink.jdbc.repository;

import com.maypink.jdbc.model.City;
import com.maypink.jdbc.model.CityWeather;
import com.maypink.jdbc.model.WeatherType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityWeatherRepository {

    List<CityWeather> getCityWeatherByCityAndWeatherType(City city, WeatherType weatherType);

    List<CityWeather> getCityWeatherByCityName(String cityName);

    CityWeather updateByCityAndWeatherType(City city, WeatherType weatherType);

    CityWeather deleteCityWeatherByCityName(String cityName);

    void save(CityWeather cityWeather);

    void delete(CityWeather cityWeather);

    List<CityWeather> findAll();
}
