package com.maypink.jdbc.repository;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.maypink.jdbc.model.CityWeather;
import com.maypink.jdbc.model.WeatherType;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;

public class CityWeatherMapper implements RowMapper<CityWeather> {

    public CityWeather mapRow(ResultSet resultSet, int i) throws SQLException {

        CityWeather cityWeather = new CityWeather();
        cityWeather.setId(resultSet.getLong("id"));
//        cityWeather.setCity(resultSet..getString("first_name"));
//        cityWeather.setWeatherType(new WeatherType(resultSet.resultSet.getString("last_name")));
        return cityWeather;
    }
}