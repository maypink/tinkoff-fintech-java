package com.maypink.jdbc.repository.impl;

import com.maypink.jdbc.model.City;
import com.maypink.jdbc.repository.CityRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityRepositoryImpl implements CityRepository {

    JdbcTemplate jdbcTemplate;

    @Override
    public void save(City city){
        jdbcTemplate.update("INSERT INTO Cities VALUES(1, ?)", city.getId(), city.getName());
    }

    @Override
    public List<City> getCityByName(String cityName) {
        return jdbcTemplate.query("SELECT * FROM Cities WHERE name=?", new BeanPropertyRowMapper<>(City.class), cityName);
    }


}
