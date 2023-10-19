package com.maypink.hibernate.service;

import com.maypink.hibernate.exception.customException.WeatherDuplicateException;
import com.maypink.hibernate.model.City;
import com.maypink.hibernate.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService {

    @Autowired
    private final CityRepository cityRepository;

    public void save(City city){
        if (!cityRepository.existsById(city.getId())){
            cityRepository.save(city);
        } else {
            throw new WeatherDuplicateException("Attempt to insert City with already existing id.");
        }
    }


}
