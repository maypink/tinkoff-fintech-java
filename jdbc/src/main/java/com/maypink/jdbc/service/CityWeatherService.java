package com.maypink.jdbc.service;

import com.maypink.jdbc.dto.CityDto;
import com.maypink.jdbc.dto.CityWeatherDto;
import com.maypink.jdbc.exception.customException.WeatherDuplicateException;
import com.maypink.jdbc.exception.customException.WeatherException;
import com.maypink.jdbc.exception.customException.WeatherNotFoundException;
import com.maypink.jdbc.model.CityWeather;
import com.maypink.jdbc.repository.CityWeatherMapper;
import com.maypink.jdbc.repository.impl.CityWeatherRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityWeatherService {
    @Autowired
    private final CityWeatherRepositoryImpl cityWeatherRepositoryImpl;

    @Autowired
    private final CityService cityService;

    @Autowired
    private final WeatherTypeService weatherTypeService;

    @Autowired
    private final CityWeatherMapper cityWeatherMapper;

    @Transactional
    public CityWeatherDto save(CityWeather cityWeather) throws WeatherException {
        if (!cityWeatherRepositoryImpl.exists(cityWeather)) {
            cityService.save(cityWeather.getCity());

            weatherTypeService.save(cityWeather.getWeatherType());

            cityWeatherRepositoryImpl.save(cityWeather);

            return cityWeatherMapper.toDto(cityWeather);
        } else {
            throw new WeatherDuplicateException("Attempt to insert CityWeather with already existing id.");
        }
    }

    @Transactional
    public void update(CityWeather cityWeather) throws WeatherException{
        List<CityWeatherDto> cityWeatherDtos = cityWeatherRepositoryImpl.getCityWeatherById(cityWeather.getId());

        // if there is no such CityWeather object
        if (cityWeatherDtos.isEmpty()) {
            save(cityWeather);
        } else {
            // there must be only zero or one weather with specified id
            CityWeatherDto cityWeatherDtoToUpdate = cityWeatherDtos.get(0);
            cityWeatherDtoToUpdate.setWeatherTypeId(cityWeather.getWeatherType().getId());
            weatherTypeService.save(cityWeather.getWeatherType());
            cityWeatherRepositoryImpl.updateWeatherTypeId(cityWeatherDtoToUpdate);
        }
    }

    public List<CityWeatherDto> getCityWeathersByCityName(String cityName) throws WeatherException{
        List<CityDto> cityDto = cityService.getCityByName(cityName);
        if (cityDto.isEmpty()) {
            throw new WeatherNotFoundException("No such object.");
        } else {
            return cityWeatherRepositoryImpl.getCityWeatherById(cityDto.get(0).getId());
        }
    }

    public CityWeatherDto deleteByCityName(String cityName) throws WeatherException{
        return cityWeatherRepositoryImpl.deleteCityWeatherByCityName(cityName);
    }
}
