package com.maypink.hibernate.repository;

import com.maypink.hibernate.model.City;
import com.maypink.hibernate.model.CityWeather;
import com.maypink.hibernate.model.WeatherType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityWeatherRepository extends JpaRepository<CityWeather, Long> {

    List<CityWeather> getCityWeatherByCityAndWeatherType(City city, WeatherType weatherType);

    boolean exists(CityWeather cityWeather);

    List<CityWeather> getCityWeatherByCityName(String cityName);

    @Modifying
    @Transactional
    @Query("update CityWeather c set c.weatherType = ?2 where c.city = ?1")
    CityWeather updateByCityAndWeatherType(City city, WeatherType weatherType);

    CityWeather deleteCityWeatherByCityName(String cityName);
}
