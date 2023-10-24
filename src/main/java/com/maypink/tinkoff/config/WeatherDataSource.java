package com.maypink.tinkoff.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;


@Component
@Getter
@Setter
public class WeatherDataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    private final WeatherConfigProperties weatherConfigProperties;

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    public WeatherDataSource(WeatherConfigProperties weatherConfigProperties) {
        this.weatherConfigProperties = weatherConfigProperties;
        config.setJdbcUrl(weatherConfigProperties.getUrl());
        config.setUsername(weatherConfigProperties.getUsername());
        config.setPassword(weatherConfigProperties.getPassword());
        ds = new HikariDataSource( config );
    }
}
