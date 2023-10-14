package com.maypink.hibernate.service;
import com.maypink.hibernate.controller.CityWeatherMapper;
import com.maypink.hibernate.dto.CityWeatherDto;
import com.maypink.hibernate.exception.ResponseWeatherErrorException;
import com.maypink.hibernate.exception.WeatherErrorResponse;
import com.maypink.hibernate.exception.WeatherErrorResponseDescription;
import com.maypink.hibernate.model.City;
import com.maypink.hibernate.model.CityWeather;
import com.maypink.hibernate.model.WeatherType;
import com.maypink.hibernate.repository.CityWeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

    public CityWeatherDto save(CityWeather cityWeather) throws ResponseWeatherErrorException{
        List<CityWeatherDto> cityWeathersDtos = show(cityWeather.getCity(), cityWeather.getWeatherType());
        if (cityWeathersDtos.isEmpty()) {
            cityService.save(cityWeather.getCity());

            weatherTypeService.save(cityWeather.getWeatherType());

            cityWeatherRepository.save(cityWeather);
            return cityWeatherMapper.toDto(cityWeather);
        } else {
            throw new ResponseWeatherErrorException(ResponseEntity.status(409).header("Attempt to insert duplicate").build());
        }
    }

    public CityWeatherDto update(CityWeather cityWeather) {
        List<CityWeather> cityWeathers = cityWeatherRepository.getCityWeatherByCityAndWeatherType(cityWeather.getCity(), cityWeather.getWeatherType());

        // if there is no such Weather object
        if (cityWeathers.isEmpty()) {
            return save(cityWeather);
        } else {
            // there must be only zero or one weather with specified date and region, since adding new object with the same data
            // is impossible
            CityWeather cityWeatherToUpdate = cityWeathers.get(0);
            cityWeatherToUpdate.setWeatherType(cityWeather.getWeatherType());
            weatherTypeService.save(cityWeather.getWeatherType());
            cityWeatherRepository.updateByCityAndWeatherType(cityWeatherToUpdate.getCity(), cityWeatherToUpdate.getWeatherType());
            return cityWeatherMapper.toDto(cityWeatherToUpdate);
        }
    }

    public void delete(CityWeather cityWeather) throws ResponseWeatherErrorException{
        List<CityWeather> cityWeathers = cityWeatherRepository.getCityWeatherByCityAndWeatherType(cityWeather.getCity(), cityWeather.getWeatherType());

        // if there is no such Weather object
        if (cityWeathers.isEmpty()) {
            throw new ResponseWeatherErrorException(ResponseEntity.status(404).header("No such object to delete.").build());
        } else {
            cityWeatherRepository.delete(cityWeather);
        }
    }

    public List<CityWeatherDto> findAll(){

        List<CityWeather> cityWeathers = cityWeatherRepository.findAll();
        return cityWeathers.stream().map(cityWeatherMapper::toDto).toList();
    }

    public List<CityWeatherDto> show(City city, WeatherType weatherType){
        List<CityWeather> cityWeathers = cityWeatherRepository.getCityWeatherByCityAndWeatherType(city, weatherType);
        return cityWeathers.stream().map(cityWeather -> cityWeatherMapper.toDto(cityWeather)).toList();
    }

    public List<CityWeatherDto> show(String cityName){
        List<CityWeather> cityWeathers = cityWeatherRepository.getCityWeatherByCityName(cityName);
        if (cityWeathers.isEmpty()) {
            throw new ResponseWeatherErrorException(ResponseEntity.status(404).header("No such object.").build());
        } else {
            return cityWeathers.stream().map(cityWeatherMapper::toDto).toList();
        }
    }

    public CityWeatherDto deleteByCityName(String cityName) throws ResponseWeatherErrorException{
        List<CityWeather> cityWeathers = cityWeatherRepository.getCityWeatherByCityName(cityName);

        // if there is no such Weather object
        if (cityWeathers.isEmpty()) {
            throw new ResponseWeatherErrorException(ResponseEntity.status(404).header("No such object to delete.").build());
        } else {
            CityWeather cityWeather = cityWeatherRepository.deleteCityWeatherByCityName(cityName);
            return cityWeatherMapper.toDto(cityWeather);
        }
    }
}
