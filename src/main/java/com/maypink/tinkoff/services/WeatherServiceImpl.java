package com.maypink.tinkoff.services;

import com.maypink.tinkoff.client.WeatherClient;
import com.maypink.tinkoff.config.WeatherDataSource;
import com.maypink.tinkoff.controllers.resources.WeatherResource;
import com.maypink.tinkoff.dto.WeatherApiResponse;
import com.maypink.tinkoff.exception.customException.WeatherDuplicateException;
import com.maypink.tinkoff.exception.customException.WeatherException;
import com.maypink.tinkoff.exception.customException.WeatherTransactionException;
import com.maypink.tinkoff.repositories.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

import static java.sql.Connection.TRANSACTION_READ_COMMITTED;


@Service
public class WeatherServiceImpl implements WeatherService{
    @Autowired
    private final WeatherRepository weatherRepository;

    @Autowired
    private final WeatherClient weatherClient;

    @Autowired
    private final WeatherDataSource weatherDataSource;

    public WeatherServiceImpl(WeatherRepository weatherRepository, WeatherClient weatherClient, WeatherDataSource weatherDataSource){
        this.weatherRepository = weatherRepository;
        this.weatherClient = weatherClient;
        this.weatherDataSource = weatherDataSource;
    }

    @Override
    public List<WeatherResource> getAllWeathers(){
        return weatherRepository.getAllWeathers();
    }


    @Override
    public List<WeatherResource> getWeatherByName(String regionName) throws WeatherException {
        return weatherRepository.existsByName(regionName);
    }

    @Override
    public WeatherResource addJdbc(WeatherResource weatherResource) throws WeatherException, SQLException {
        List<WeatherResource> weathers = getWeatherByName(weatherResource.name());
        if (weathers.isEmpty()) {
            Connection connection = weatherDataSource.getConnection();
            DatabaseMetaData dbmd = connection.getMetaData();
            if (dbmd.supportsTransactionIsolationLevel(TRANSACTION_READ_COMMITTED)) {
                connection.setTransactionIsolation(TRANSACTION_READ_COMMITTED);
            }
            try (connection) {
                connection.setAutoCommit(false);
                weatherRepository.addWeather(weatherResource);
                connection.commit();
                return weatherResource;

            } catch (SQLException e) {
                connection.rollback();
                throw new WeatherTransactionException("Transaction was not successful.");
            }
        } else {
            throw new WeatherDuplicateException("Attempt to insert duplicate of Weather.");
        }
    }

    @Override
    public WeatherResource addSpring(WeatherResource weatherResource) throws WeatherException {
        List<WeatherResource> weathers = getWeatherByName(weatherResource.name());
        if (weathers.isEmpty()) {
            weatherRepository.addWeather(weatherResource);
            return weatherResource;
        } else {
            throw new WeatherDuplicateException("Attempt to insert duplicate of Weather.");
        }
    }

    @Override
    public WeatherApiResponse getWeather(String key){
        return weatherClient.getWeather(key);
    }
}
