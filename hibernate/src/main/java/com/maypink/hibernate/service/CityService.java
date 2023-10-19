package com.maypink.hibernate.service;

import com.maypink.hibernate.model.City;
import com.maypink.hibernate.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    @Autowired
    private final CityRepository cityRepository;

    public void save(City city){
        if (!cityRepository.exists(city)){
            cityRepository.save(city);
        }
    }


}
