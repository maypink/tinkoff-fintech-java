package com.maypink.jdbc.repository.impl;

import com.maypink.jdbc.dto.WeatherTypeDto;
import com.maypink.jdbc.model.WeatherType;
import com.maypink.jdbc.repository.WeatherTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WeatherTypeRepositoryImpl implements WeatherTypeRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    WeatherTypeDto weatherTypeDto;

    @Override
    public void save(WeatherType weatherType){
        jdbcTemplate.update("INSERT INTO WeatherTypes VALUES(1, ?)", weatherType.getId(), weatherType.getType());
    }

    @Override
    public List<WeatherTypeDto> getWeatherTypeByType(String weatherType) {
        return jdbcTemplate.query("SELECT * FROM Cities WHERE type=?", new BeanPropertyRowMapper<>(WeatherTypeDto.class), weatherType);
    }
}
