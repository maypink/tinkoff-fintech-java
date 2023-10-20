package com.maypink.hibernate.service;

import com.maypink.hibernate.exception.customException.WeatherDuplicateException;
import com.maypink.hibernate.exception.customException.WeatherException;
import com.maypink.hibernate.model.WeatherType;
import com.maypink.hibernate.repository.WeatherTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherTypeService {
    @Autowired
    private final WeatherTypeRepository weatherTypeRepository;

    public void save(WeatherType weatherType) throws WeatherException {
        if (weatherTypeRepository.existsById(weatherType.getId())){
            throw new WeatherDuplicateException("Attempt to insert WeatherType with already existing id.");
        } else weatherTypeRepository.save(weatherType);
    }
}
