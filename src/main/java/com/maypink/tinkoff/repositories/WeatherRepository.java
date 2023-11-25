package com.maypink.tinkoff.repositories;

import com.maypink.tinkoff.controllers.resources.WeatherMapper;
import com.maypink.tinkoff.controllers.resources.WeatherResource;
import com.maypink.tinkoff.dto.WeatherDtoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WeatherRepository {

    @Autowired
    WeatherMapper weatherMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<WeatherResource> existsByName(String name){
        List<WeatherDtoDB> weatherDtoDBs = jdbcTemplate.query("SELECT * FROM weathers_data WHERE name=?",
                new BeanPropertyRowMapper<>(WeatherDtoDB.class), name);
        return weatherDtoDBs.stream().map(weather -> weatherMapper.toResource(weather)).toList();
    }

    public List<WeatherResource> existsByNameAndDateTime(String name, String dateTime){
        List<WeatherDtoDB> weatherDtoDBs = jdbcTemplate.query("SELECT * FROM weathers_data WHERE name=? AND date_time=?",
                new BeanPropertyRowMapper<>(WeatherDtoDB.class), name, dateTime);
        return weatherDtoDBs.stream().map(weather -> weatherMapper.toResource(weather)).toList();
    }

    public List<WeatherResource> getAllWeathers(){
        List<WeatherDtoDB> weatherDtoDBs = jdbcTemplate.query("SELECT * FROM weathers_data", new BeanPropertyRowMapper<>(WeatherDtoDB.class));
        return weatherDtoDBs.stream().map(weather -> weatherMapper.toResource(weather)).toList();
    }

    public WeatherResource addWeather(WeatherResource weatherResource){
        jdbcTemplate.update("INSERT INTO weathers_data (name, region, country, temp_c, temp_f, date_time) " +
                        "VALUES(?, ?, ?, ?, ?, ?)",
                weatherResource.name(), weatherResource.region(), weatherResource.country(),
                weatherResource.tempC(), weatherResource.tempF(), weatherResource.dateTime());
        return weatherResource;
    }

    public WeatherResource updateWeather(WeatherResource weatherResource){
        jdbcTemplate.update("update weathers_data set region=?, country=?, temp_c=?, temp_f=? where name=?, date_time=?",
                weatherResource.region(), weatherResource.country(),
                weatherResource.tempC(), weatherResource.tempF(), weatherResource.name(), weatherResource.dateTime());
        return weatherResource;
    }


}
