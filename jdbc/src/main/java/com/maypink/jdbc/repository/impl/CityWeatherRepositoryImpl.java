package com.maypink.jdbc.repository.impl;

import com.maypink.jdbc.exception.ResponseWeatherErrorException;
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

import static java.util.Collections.emptyList;

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
        List<CityWeatherDto> cityWeathersDtos = jdbcTemplate.query("SELECT * FROM CityWeather WHERE cityId=? and weatherTypeId=?",
                new BeanPropertyRowMapper<>(CityWeatherDto.class),  city.getId(), weatherType.getId());
        if (cityWeathersDtos.isEmpty()) {
            return emptyList();
        } else return cityWeathersDtos;
    }

    public List<CityWeatherDto> getCityWeatherById(Long cityId) {
        return jdbcTemplate.query("SELECT * FROM CityWeather WHERE cityId=?",
                new BeanPropertyRowMapper<>(CityWeatherDto.class), cityId);
    };

    public CityWeatherDto updateByCityIdAndWeatherTypeId(Long cityId, Long weatherTypeId) {
        List<CityWeatherDto> cityWeathersDtos = jdbcTemplate.query("update CityWeather set cityId = ?, weatherTypeId = ?", new BeanPropertyRowMapper<>(CityWeatherDto.class), cityId, weatherTypeId);
        jdbcTemplate.update("update CityWeather set cityId = ?, weatherTypeId = ?", cityId, weatherTypeId);
        return cityWeathersDtos.get(0);
    };

    public CityWeatherDto deleteCityWeatherByCityName(String cityName) throws ResponseWeatherErrorException{
        List<Long> cityId = jdbcTemplate.query("SELECT id FROM Cities WHERE name=?", new BeanPropertyRowMapper<>(Long.class), cityName);
        if (!cityId.isEmpty()) {
            List<CityWeatherDto> cityWeathers = jdbcTemplate.query("select * from CityWeather where cityId = ?", new BeanPropertyRowMapper<>(CityWeatherDto.class), cityId);
            jdbcTemplate.update("delete from CityWeather where cityId = ?", cityId);
            return cityWeathers.get(0);
        } else {
            throw new ResponseWeatherErrorException(ResponseEntity.status(404).header("No such object to delete.").build());
        }
    };
}
