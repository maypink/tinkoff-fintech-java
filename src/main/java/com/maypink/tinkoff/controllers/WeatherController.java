package com.maypink.tinkoff.controllers;


import com.maypink.tinkoff.models.Weather;
import com.maypink.tinkoff.services.WeatherServiceImpl;
import com.maypink.tinkoff.util.WeatherValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherServiceImpl weatherService;
    private final WeatherValidator weatherValidator;

    public WeatherController(WeatherServiceImpl weatherService, WeatherValidator weatherValidator){
        this.weatherService = weatherService;
        this.weatherValidator = weatherValidator;
    }

    @Operation(
            summary = "Get weather",
            description = "Get weather object by region name and current date"
    )
    @GetMapping("/{regionName}")
    public ResponseEntity<?> get(@PathVariable @Parameter(description = "Region name") String regionName){
        List<Weather> weatherObject = weatherService.getWeatherByRegionNameAndDate(regionName, LocalDate.now());
        if (weatherObject.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Weather object is not found");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(weatherObject.get(0));
        }
    }

    @Operation(
            summary = "Add new region",
            description = "Add new region with specified region name, temperature and date."
    )
    @PostMapping("/{regionName}")
    public ResponseEntity<?> add(@PathVariable @Parameter(description = "Region name") String regionName,
                                 @RequestBody @Parameter(description = "Temperature") String temperature,
                                 @RequestBody @Parameter(description = "Date") String date,
                                 BindingResult bindingResult){
        weatherValidator.validateStringParams(regionName, temperature, date, bindingResult);

        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation error");
        }
        else {
            int parsedTemperature = Integer.parseInt(temperature);
            LocalDate parsedDate = LocalDate.parse(date);
            List<Weather> weatherList = weatherService.getWeatherByRegionNameAndDate(regionName, parsedDate);
            if (weatherList.isEmpty()) {
                Weather weather = weatherService.add(regionName, parsedTemperature, parsedDate);
                return ResponseEntity.status(HttpStatus.CREATED).body(weather);
            } else {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Weather object with the following region and date already exists");
            }
        }
    }

    @Operation(
            summary = "Update weather",
            description = "Update weather with specified region name, temperature and date"
    )
    @PutMapping("/{regionName}")
    public ResponseEntity<?> put(@PathVariable @Parameter(description = "Region name") String regionName,
                                 @RequestBody @Parameter(description = "Temperature") String temperature,
                                 @RequestBody @Parameter(description = "Date") String date,
                                 BindingResult bindingResult){
        weatherValidator.validateStringParams(regionName, temperature, date, bindingResult);

        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation error.");
        }
        else {
            int parsedTemperature = Integer.parseInt(temperature);
            LocalDate parsedDate = LocalDate.parse(date);
            Weather weather = weatherService.update(regionName, parsedTemperature, parsedDate);
            return ResponseEntity.status(HttpStatus.OK).body(weather);
        }
    }

    @Operation(
            summary = "Delete all Weather object for specified region",
            description = "Delete all Weather object for specified region"
    )
    @DeleteMapping("/{regionName}")
    public ResponseEntity<?> delete(@PathVariable @Parameter(description = "Region name") String regionName,
                                    BindingResult bindingResult){

        weatherValidator.validateStringParams(regionName, bindingResult);

        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation error.");
        }
        else {
            Optional<List<Weather>> weatherList = weatherService.deleteByRegionName(regionName);
            if (weatherList.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(weatherList);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Weather with specified params was not found.");
            }
        }
    }
}
