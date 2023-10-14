package com.maypink.jdbc.service;

import com.maypink.jdbc.model.WeatherType;
import com.maypink.jdbc.repository.impl.WeatherTypeRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherTypeService {
    @Autowired
    private final WeatherTypeRepositoryImpl weatherTypeRepositoryImpl;

    public void save(WeatherType weatherType){
        List<WeatherType> weatherTypes = weatherTypeRepositoryImpl.getWeatherTypeByType(weatherType.getType());
        if (weatherTypes.isEmpty()){
            weatherTypeRepositoryImpl.save(weatherType);
        }
    }
}
