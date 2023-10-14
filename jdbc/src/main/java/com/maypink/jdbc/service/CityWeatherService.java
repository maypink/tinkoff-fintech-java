package com.maypink.jdbc.service;

import com.maypink.jdbc.controller.CityWeatherMapper;
import com.maypink.jdbc.dto.CityWeatherDto;
import com.maypink.jdbc.exception.ResponseWeatherErrorException;
import com.maypink.jdbc.model.City;
import com.maypink.jdbc.model.CityWeather;
import com.maypink.jdbc.model.WeatherType;
import com.maypink.jdbc.repository.impl.CityWeatherRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public CityWeatherDto save(CityWeather cityWeather) throws ResponseWeatherErrorException{
        List<CityWeatherDto> cityWeathersDtos = show(cityWeather.getCity(), cityWeather.getWeatherType());
        if (cityWeathersDtos.isEmpty()) {
            cityService.save(cityWeather.getCity());

            weatherTypeService.save(cityWeather.getWeatherType());

            cityWeatherRepositoryImpl.save(cityWeather);
            return cityWeatherMapper.toDto(cityWeather);
        } else {
            throw new ResponseWeatherErrorException(ResponseEntity.status(409).header("Attempt to insert duplicate").build());
        }
    }

    public CityWeatherDto update(CityWeather cityWeather) {
        List<CityWeather> cityWeathers = cityWeatherRepositoryImpl.getCityWeatherByCityAndWeatherType(cityWeather.getCity(), cityWeather.getWeatherType());

        // if there is no such Weather object
        if (cityWeathers.isEmpty()) {
            return save(cityWeather);
        } else {
            // there must be only zero or one weather with specified date and region, since adding new object with the same data
            // is impossible
            CityWeather cityWeatherToUpdate = cityWeathers.get(0);
            cityWeatherToUpdate.setWeatherType(cityWeather.getWeatherType());
            weatherTypeService.save(cityWeather.getWeatherType());
            cityWeatherRepositoryImpl.updateByCityAndWeatherType(cityWeatherToUpdate.getCity(), cityWeatherToUpdate.getWeatherType());
            return cityWeatherMapper.toDto(cityWeatherToUpdate);
        }
    }

    public void delete(CityWeather cityWeather) throws ResponseWeatherErrorException{
        List<CityWeather> cityWeathers = cityWeatherRepositoryImpl.getCityWeatherByCityAndWeatherType(cityWeather.getCity(), cityWeather.getWeatherType());

        // if there is no such Weather object
        if (cityWeathers.isEmpty()) {
            throw new ResponseWeatherErrorException(ResponseEntity.status(404).header("No such object to delete.").build());
        } else {
            cityWeatherRepositoryImpl.delete(cityWeather);
        }
    }

    public List<CityWeatherDto> findAll(){

        List<CityWeather> cityWeathers = (List<CityWeather>) cityWeatherRepositoryImpl.findAll();
        return cityWeathers.stream().map(cityWeatherMapper::toDto).toList();
    }

    public List<CityWeatherDto> show(City city, WeatherType weatherType){
        List<CityWeather> cityWeathers = cityWeatherRepositoryImpl.getCityWeatherByCityAndWeatherType(city, weatherType);
        return cityWeathers.stream().map(cityWeather -> cityWeatherMapper.toDto(cityWeather)).toList();
    }

    public List<CityWeatherDto> show(String cityName){
        List<CityWeather> cityWeathers = cityWeatherRepositoryImpl.getCityWeatherByCityName(cityName);
        if (cityWeathers.isEmpty()) {
            throw new ResponseWeatherErrorException(ResponseEntity.status(404).header("No such object.").build());
        } else {
            return cityWeathers.stream().map(cityWeatherMapper::toDto).toList();
        }
    }

    public CityWeatherDto deleteByCityName(String cityName) throws ResponseWeatherErrorException{
        List<CityWeather> cityWeathers = cityWeatherRepositoryImpl.getCityWeatherByCityName(cityName);

        // if there is no such Weather object
        if (cityWeathers.isEmpty()) {
            throw new ResponseWeatherErrorException(ResponseEntity.status(404).header("No such object to delete.").build());
        } else {
            CityWeather cityWeather = cityWeatherRepositoryImpl.deleteCityWeatherByCityName(cityName);
            return cityWeatherMapper.toDto(cityWeather);
        }
    }
}
