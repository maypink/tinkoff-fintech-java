package com.maypink.jdbc.repository;

import com.maypink.jdbc.model.WeatherType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherTypeRepository {

    void save(WeatherType weatherType);

    List<WeatherType> getWeatherTypeByType(String weatherType);

}
