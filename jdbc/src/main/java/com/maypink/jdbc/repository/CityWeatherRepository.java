package com.maypink.jdbc.repository;

import com.maypink.jdbc.dto.CityWeatherDto;
import com.maypink.jdbc.model.City;
import com.maypink.jdbc.model.CityWeather;
import com.maypink.jdbc.model.WeatherType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityWeatherRepository {

    List<CityWeatherDto> getCityWeatherByCityAndWeatherType(City city, WeatherType weatherType);

    List<CityWeatherDto> getCityWeatherById(Long cityName);

    CityWeatherDto deleteCityWeatherByCityName(String cityName);

    void save(CityWeather cityWeather);

    void delete(CityWeather cityWeather);

    List<CityWeatherDto> findAll();
}
