package com.maypink.jdbc.service;

import com.maypink.jdbc.dto.CityDto;
import com.maypink.jdbc.model.City;
import com.maypink.jdbc.repository.impl.CityRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    @Autowired
    private final CityRepositoryImpl cityRepositoryImpl;

    public void save(City city){
        List<CityDto> cities = cityRepositoryImpl.getCityByName(city.getName());
        if (cities.isEmpty()){
            cityRepositoryImpl.save(city);
        }
    }

    public List<CityDto> getCityByName(String cityName){
        return cityRepositoryImpl.getCityByName(cityName);
    }




}
