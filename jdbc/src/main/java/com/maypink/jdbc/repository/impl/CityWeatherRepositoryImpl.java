package com.maypink.jdbc.repository.impl;

import com.maypink.jdbc.exception.ResponseWeatherErrorException;
import com.maypink.jdbc.exception.customException.WeatherException;
import com.maypink.jdbc.exception.customException.WeatherNotFoundException;
import com.maypink.jdbc.model.City;
import com.maypink.jdbc.model.CityWeather;
import com.maypink.jdbc.model.WeatherType;
import com.maypink.jdbc.repository.CityWeatherRepository;
import com.maypink.jdbc.dto.CityWeatherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityWeatherRepositoryImpl implements CityWeatherRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void save(CityWeather cityWeather){
        jdbcTemplate.update("INSERT INTO CityWeather (id, cityId, weatherTypeId) VALUES(?, ?, ?)",
                cityWeather.getId(), cityWeather.getCity().getId(), cityWeather.getWeatherType().getId());
    }

    public void delete(CityWeather cityWeather) {
        jdbcTemplate.update("DELETE FROM CityWeather WHERE id=?", cityWeather.getId());
    }

    public List<CityWeatherDto> findAll() {
        return jdbcTemplate.query("SELECT * FROM CityWeather", new BeanPropertyRowMapper<>(CityWeatherDto.class));
    }

    public List<CityWeatherDto> getCityWeatherByCityAndWeatherType(City city, WeatherType weatherType) {
        return jdbcTemplate.query("SELECT * FROM CityWeather WHERE cityId=? and weatherTypeId=?",
                new BeanPropertyRowMapper<>(CityWeatherDto.class),  city.getId(), weatherType.getId());
    }

    public boolean exists(CityWeather cityWeather){
        List<CityWeatherDto> cityWeatherDtos = getCityWeatherById(cityWeather.getId());
        return !cityWeatherDtos.isEmpty();
    }

    public List<CityWeatherDto> getCityWeatherById(Long cityId) {
        return jdbcTemplate.query("SELECT * FROM CityWeather WHERE cityId=?",
                new BeanPropertyRowMapper<>(CityWeatherDto.class), cityId);
    };

    public int updateWeatherTypeId(CityWeatherDto cityWeatherDto) {
        return jdbcTemplate.update("update CityWeather set weatherTypeId = ? where id=?", cityWeatherDto.getWeatherTypeId(), cityWeatherDto.getId());
    };

    public CityWeatherDto deleteCityWeatherByCityName(String cityName) throws WeatherException {
        List<CityWeatherDto> cityWeatherDtos = jdbcTemplate.query("select CityWeather.id, CityWeather.cityId, CityWeather.weatherTypeId from CityWeather join Cities on CityWeather.cityId=Cities.id where Cities.name=?", new BeanPropertyRowMapper<>(CityWeatherDto.class), cityName);
        if (!cityWeatherDtos.isEmpty()) {
            // there can be zero or one id for the exact city name
            CityWeatherDto cityWeatherDto = cityWeatherDtos.get(0);
            jdbcTemplate.update("delete from CityWeather where cityId = ?", cityWeatherDto.getCityId());
            return cityWeatherDto;
        } else {
            throw new WeatherNotFoundException("No such object to delete");
        }
    };
}
