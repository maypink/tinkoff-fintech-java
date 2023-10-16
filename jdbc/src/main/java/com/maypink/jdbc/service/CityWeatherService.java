package com.maypink.jdbc.service;

import com.maypink.jdbc.dto.CityDto;
import com.maypink.jdbc.dto.CityWeatherDto;
import com.maypink.jdbc.exception.ResponseWeatherErrorException;
import com.maypink.jdbc.model.City;
import com.maypink.jdbc.model.CityWeather;
import com.maypink.jdbc.model.WeatherType;
import com.maypink.jdbc.repository.CityWeatherMapper;
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
        List<CityWeatherDto> cityWeatherDtos = cityWeatherRepositoryImpl.getCityWeatherByCityAndWeatherType(cityWeather.getCity(), cityWeather.getWeatherType());

        // if there is no such Weather object
        if (cityWeatherDtos.isEmpty()) {
            return save(cityWeather);
        } else {
            // there must be only zero or one weather with specified date and region, since adding new object with the same data
            // is impossible
            CityWeatherDto cityWeatherDtoToUpdate = cityWeatherDtos.get(0);
            cityWeatherDtoToUpdate.setWeatherTypeId(cityWeather.getWeatherType().getId());
            weatherTypeService.save(cityWeather.getWeatherType());
            cityWeatherRepositoryImpl.updateByCityIdAndWeatherTypeId(cityWeatherDtoToUpdate.getCityId(), cityWeatherDtoToUpdate.getWeatherTypeId());
            return cityWeatherDtoToUpdate;
        }
    }

    public void delete(CityWeather cityWeather) throws ResponseWeatherErrorException{
        List<CityWeatherDto> cityWeatherDtos = cityWeatherRepositoryImpl.getCityWeatherByCityAndWeatherType(cityWeather.getCity(), cityWeather.getWeatherType());

        // if there is no such Weather object
        if (cityWeatherDtos.isEmpty()) {
            throw new ResponseWeatherErrorException(ResponseEntity.status(404).header("No such object to delete.").build());
        } else {
            cityWeatherRepositoryImpl.delete(cityWeather);
        }
    }

    public List<CityWeatherDto> findAll(){

        return cityWeatherRepositoryImpl.findAll();
    }

    public List<CityWeatherDto> show(City city, WeatherType weatherType){
        return cityWeatherRepositoryImpl.getCityWeatherByCityAndWeatherType(city, weatherType);
    }

    public List<CityWeatherDto> show(String cityName){
        List<CityDto> cityDto = cityService.getCityByName(cityName);
        if (cityDto.isEmpty()) {
            throw new ResponseWeatherErrorException(ResponseEntity.status(404).header("No such object.").build());
        } else {
            return cityWeatherRepositoryImpl.getCityWeatherById(cityDto.get(0).getId());
        }
    }

    public CityWeatherDto deleteByCityName(String cityName) throws ResponseWeatherErrorException{
        return cityWeatherRepositoryImpl.deleteCityWeatherByCityName(cityName);
    }
}
