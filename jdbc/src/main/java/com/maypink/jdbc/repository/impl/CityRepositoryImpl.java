package com.maypink.jdbc.repository.impl;

import com.maypink.jdbc.dto.CityDto;
import com.maypink.jdbc.dto.CityWeatherDto;
import com.maypink.jdbc.model.City;
import com.maypink.jdbc.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityRepositoryImpl implements CityRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void save(City city){
        jdbcTemplate.update("INSERT INTO Cities (id, name) VALUES(?, ?)", city.getId(), city.getName());
    }

    @Override
    public List<CityDto> getCityByName(String cityName) {
        return jdbcTemplate.query("SELECT * FROM Cities WHERE name=?", new BeanPropertyRowMapper<>(CityDto.class), cityName);
    }


}
