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
        List<WeatherDtoDB> weatherDtoDBs = jdbcTemplate.query("SELECT * FROM Weather WHERE name=?",
                new BeanPropertyRowMapper<>(WeatherDtoDB.class), name);
        return weatherDtoDBs.stream().map(weather -> weatherMapper.toResource(weather)).toList();
    }

    public List<WeatherResource> getAllWeathers(){
        List<WeatherDtoDB> weatherDtoDBs = jdbcTemplate.query("SELECT * FROM Weather", new BeanPropertyRowMapper<>(WeatherDtoDB.class));
        return weatherDtoDBs.stream().map(weather -> weatherMapper.toResource(weather)).toList();
    }

    public WeatherResource addWeather(WeatherResource weatherResource){
        jdbcTemplate.update("INSERT INTO Weather (name, region, country, tempC, tempF) " +
                        "VALUES(?, ?, ?, ?, ?)",
                weatherResource.name(), weatherResource.region(), weatherResource.country(),
                weatherResource.tempC(), weatherResource.tempF());
        return weatherResource;
    }
}
