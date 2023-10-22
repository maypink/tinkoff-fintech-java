package com.maypink.tinkoff.controllers;


import com.maypink.tinkoff.config.WeatherDataSource;
import com.maypink.tinkoff.controllers.resources.WeatherMapper;
import com.maypink.tinkoff.controllers.resources.WeatherResource;
import com.maypink.tinkoff.exception.ResponseWeatherErrorException;
import com.maypink.tinkoff.exception.customException.WeatherNotFoundException;
import com.maypink.tinkoff.services.WeatherServiceImpl;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

import static java.sql.Connection.TRANSACTION_READ_COMMITTED;
import static java.sql.Connection.TRANSACTION_SERIALIZABLE;


@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherServiceImpl weatherService;
    private final WeatherMapper weatherMapper;

    private final WeatherDataSource weatherDataSource;

    @Operation(
            summary = "Get weather from H2 database."
    )
    @GetMapping("/{name}")
    public ResponseEntity<?> get(@PathVariable @Parameter(description = "name") @NotEmpty @Size(max = 15) String name) {

        List<WeatherResource> weatherResources = weatherService.getWeatherByName(name);
        if (weatherResources.isEmpty()){
            throw new WeatherNotFoundException("Weather with specified name not found");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(weatherResources.get(0));
        }
    }

    @Operation(
            summary = "Get all weathers from H2 database."
    )
    @GetMapping("/all")
    public ResponseEntity<?> getAllWeathers() {
        List<WeatherResource> weatherResources = weatherService.getAllWeathers();
        return ResponseEntity.status(HttpStatus.OK).body(weatherResources);
    }

    @Operation(
            summary = "Add weather from Weather Service"
    )
    @PostMapping("/newJdbc")
    public ResponseEntity<?> addWithJdbcTransaction(@RequestBody @Valid String query, BindingResult bindingResult) throws ResponseWeatherErrorException, SQLException {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation error");
        } else {
            Connection connection = weatherDataSource.getJdbcDataSource().getConnection();
            DatabaseMetaData dbmd = connection.getMetaData();
            if (dbmd.supportsTransactionIsolationLevel(TRANSACTION_READ_COMMITTED)) {
                connection.setTransactionIsolation(TRANSACTION_READ_COMMITTED);
            }
            try (connection) {
                connection.setAutoCommit(false);
                WeatherResource weatherResource = weatherMapper.toResource(weatherService.getWeather(query));
                weatherService.add(weatherResource);
                connection.commit();
                return ResponseEntity.status(HttpStatus.CREATED).body(weatherResource);

            } catch (SQLException e) {
                connection.rollback();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong.");
            }
        }
    }

    // since it is read and add only app, so there is no such need to double check rows after the first read (it can not be modified)
    // on the other hand, we want to get commited only information
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Operation(
            summary = "Add weather from Weather Service"
    )
    @PostMapping("/newSpring")
    public ResponseEntity<?> addWithSpringTransaction(@RequestBody @Valid String query, BindingResult bindingResult) throws ResponseWeatherErrorException {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation error");
        } else {
            WeatherResource weatherResource = weatherMapper.toResource(weatherService.getWeather(query));
            weatherService.add(weatherResource);
            return ResponseEntity.status(HttpStatus.CREATED).body(weatherResource);
        }
    }

    @Operation(
            summary = "Get weather from Weather Service"
    )
    @GetMapping
    @RateLimiter(name = "rateLimiterApi")
    public ResponseEntity<?> getWeather(@RequestParam String query) throws ResponseWeatherErrorException {
        return ResponseEntity.ok(weatherMapper.toResource(weatherService.getWeather(query)));
    }
}
