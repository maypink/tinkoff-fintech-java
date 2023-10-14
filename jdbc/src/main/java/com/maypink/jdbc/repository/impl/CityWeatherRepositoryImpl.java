package com.maypink.jdbc.repository.impl;

import com.maypink.jdbc.exception.ResponseWeatherErrorException;
import com.maypink.jdbc.model.City;
import com.maypink.jdbc.model.CityWeather;
import com.maypink.jdbc.model.WeatherType;
import com.maypink.jdbc.repository.CityWeatherRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.Collections.emptyList;

@Repository
public class CityWeatherRepositoryImpl implements CityWeatherRepository {

    JdbcTemplate jdbcTemplate;

    public void save(CityWeather cityWeather){
        jdbcTemplate.update("INSERT INTO CityWeather VALUES(1, ?, ?)",
                cityWeather.getId(), cityWeather.getCity().getId(), cityWeather.getWeatherType().getId());
    }

    public void delete(CityWeather cityWeather) {
        jdbcTemplate.update("DELETE FROM CityWeather WHERE id=?", cityWeather.getId());
    }

    public List<CityWeather> findAll() {
        return jdbcTemplate.query("SELECT * FROM CityWeather", new BeanPropertyRowMapper<>(CityWeather.class));
    }

    public List<CityWeather> getCityWeatherByCityAndWeatherType(City city, WeatherType weatherType) {
        return jdbcTemplate.query("SELECT * FROM CityWeather WHERE cityId=? and weatherId=?",
                new BeanPropertyRowMapper<>(CityWeather.class),  city.getId(), weatherType.getId());
    };

    public List<CityWeather> getCityWeatherByCityName(String cityName) {
        List<Integer> cityId = jdbcTemplate.query("SELECT id FROM Cities WHERE name=?", new BeanPropertyRowMapper<>(Integer.class), cityName);
        if (cityId.isEmpty()) {
            return emptyList();
        } else {
            return jdbcTemplate.query("SELECT * FROM CityWeather WHERE city_id=?",
                    new BeanPropertyRowMapper<>(CityWeather.class), cityId);
        }
    };

    public CityWeather updateByCityAndWeatherType(City city, WeatherType weatherType) {
        List<CityWeather> cityWeathers = jdbcTemplate.query("update cityWeather set cityId = ?, weatherTypeId = ?", new BeanPropertyRowMapper<>(CityWeather.class), city.getId(), weatherType.getId());
        jdbcTemplate.update("update cityWeather set cityId = ?, weatherTypeId = ?", city.getId(), weatherType.getId());
        return cityWeathers.get(0);
    };

    public CityWeather deleteCityWeatherByCityName(String cityName) throws ResponseWeatherErrorException{
        List<Integer> cityId = jdbcTemplate.query("SELECT id FROM cities WHERE name=?", new BeanPropertyRowMapper<>(Integer.class), cityName);
        if (!cityId.isEmpty()) {
            List<CityWeather> cityWeathers = jdbcTemplate.query("select * from cityWeather where cityId = ?", new BeanPropertyRowMapper<>(CityWeather.class), cityId);
            jdbcTemplate.update("delete from cityWeather where cityId = ?", cityId);
            return cityWeathers.get(0);
        } else {
            throw new ResponseWeatherErrorException(ResponseEntity.status(404).header("No such object to delete.").build());
        }
    };
}
