package com.maypink.tinkoff.config;

import lombok.Getter;
import lombok.Setter;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class WeatherDataSource {

    private JdbcDataSource jdbcDataSource;

    private final WeatherConfigProperties weatherConfigProperties;


    public WeatherDataSource(WeatherConfigProperties weatherConfigProperties) {
        this.weatherConfigProperties = weatherConfigProperties;
        this.jdbcDataSource = new JdbcDataSource();
        this.jdbcDataSource.setURL(weatherConfigProperties.getUrl());
        this.jdbcDataSource.setUser(weatherConfigProperties.getUsername());
        this.jdbcDataSource.setPassword(weatherConfigProperties.getPassword());
    }
}
