package com.maypink.jdbc.repository;
import com.maypink.jdbc.dto.CityWeatherDto;
import com.maypink.jdbc.model.CityWeather;
import org.springframework.stereotype.Component;

@Component
public class CityWeatherMapper {

    public CityWeatherDto toDto(CityWeather cityWeather){
        CityWeatherDto cityWeatherDto = new CityWeatherDto();
        cityWeatherDto.setId(cityWeather.getId());
        cityWeatherDto.setCityId(cityWeather.getCity().getId());
        cityWeatherDto.setWeatherTypeId(cityWeather.getWeatherType().getId());
        return cityWeatherDto;
    }
}