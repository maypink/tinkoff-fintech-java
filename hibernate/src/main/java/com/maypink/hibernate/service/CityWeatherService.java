package com.maypink.hibernate.service;
import com.maypink.hibernate.controller.CityWeatherMapper;
import com.maypink.hibernate.dto.CityWeatherDto;
import com.maypink.hibernate.exception.ResponseWeatherErrorException;
import com.maypink.hibernate.exception.customException.WeatherDuplicateException;
import com.maypink.hibernate.exception.customException.WeatherNotFoundException;
import com.maypink.hibernate.model.City;
import com.maypink.hibernate.model.CityWeather;
import com.maypink.hibernate.model.WeatherType;
import com.maypink.hibernate.repository.CityWeatherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityWeatherService {
    @Autowired
    private final CityWeatherRepository cityWeatherRepository;

    @Autowired
    private final CityService cityService;

    @Autowired
    private final WeatherTypeService weatherTypeService;

    @Autowired
    private final CityWeatherMapper cityWeatherMapper;

    @Transactional
    public CityWeatherDto save(CityWeather cityWeather) throws ResponseWeatherErrorException{
        List<CityWeatherDto> cityWeathersDtos = getCityWeathers(cityWeather.getCity(), cityWeather.getWeatherType());
        if (cityWeathersDtos.isEmpty()) {
            cityService.save(cityWeather.getCity());

            weatherTypeService.save(cityWeather.getWeatherType());

            cityWeatherRepository.save(cityWeather);
            return cityWeatherMapper.toDto(cityWeather);
        } else {
            throw new WeatherDuplicateException("Attempt to insert CityWeather with already existing id.");
        }
    }

    @Transactional
    public CityWeatherDto update(CityWeather cityWeather) {
        List<CityWeather> cityWeathers = cityWeatherRepository.getCityWeatherByCityAndWeatherType(cityWeather.getCity(), cityWeather.getWeatherType());

        // if there is no such Weather object
        if (cityWeathers.isEmpty()) {
            return save(cityWeather);
        } else {
            // there must be only zero or one weather with specified data, since adding new object with the same data
            // is impossible
            CityWeather cityWeatherToUpdate = cityWeathers.get(0);
            cityWeatherToUpdate.setWeatherType(cityWeather.getWeatherType());
            weatherTypeService.save(cityWeather.getWeatherType());
            cityWeatherRepository.updateByCityAndWeatherType(cityWeatherToUpdate.getCity(), cityWeatherToUpdate.getWeatherType());
            return cityWeatherMapper.toDto(cityWeatherToUpdate);
        }
    }

    public List<CityWeatherDto> getCityWeathers(City city, WeatherType weatherType){
        List<CityWeather> cityWeathers = cityWeatherRepository.getCityWeatherByCityAndWeatherType(city, weatherType);
        return cityWeathers.stream().map(cityWeatherMapper::toDto).toList();
    }

    public List<CityWeatherDto> getCityWeathers(String cityName){
        List<CityWeather> cityWeathers = cityWeatherRepository.getCityWeatherByCityName(cityName);
        if (cityWeathers.isEmpty()) {
            throw new WeatherNotFoundException("No such object.");
        } else {
            return cityWeathers.stream().map(cityWeatherMapper::toDto).toList();
        }
    }

    public CityWeatherDto deleteByCityName(String cityName) throws ResponseWeatherErrorException{
        List<CityWeather> cityWeathers = cityWeatherRepository.getCityWeatherByCityName(cityName);

        // if there is no such Weather object
        if (cityWeathers.isEmpty()) {
            throw new WeatherNotFoundException("No such object CityWeather to delete.");
        } else {
            CityWeather cityWeather = cityWeathers.get(0);
            cityWeatherRepository.deleteCityWeatherById(cityWeather.getId());
            return cityWeatherMapper.toDto(cityWeather);
        }
    }
}
