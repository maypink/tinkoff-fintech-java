package com.maypink.jdbc.service;

import com.maypink.jdbc.model.WeatherType;
import com.maypink.jdbc.repository.WeatherTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherTypeService {
    @Autowired
    private final WeatherTypeRepository weatherTypeRepository;

    public void save(WeatherType weatherType){
        List<WeatherType> weatherTypes = weatherTypeRepository.getWeatherTypeByType(weatherType.getType());
        if (weatherTypes.isEmpty()){
            weatherTypeRepository.save(weatherType);
        }
    }
}
