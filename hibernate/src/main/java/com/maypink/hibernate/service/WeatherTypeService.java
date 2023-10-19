package com.maypink.hibernate.service;

import com.maypink.hibernate.model.City;
import com.maypink.hibernate.model.WeatherType;
import com.maypink.hibernate.repository.CityRepository;
import com.maypink.hibernate.repository.WeatherTypeRepository;
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
        if (!weatherTypeRepository.exists(weatherType)){
            weatherTypeRepository.save(weatherType);
        }
    }
}
