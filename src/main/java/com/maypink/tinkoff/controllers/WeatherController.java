package com.maypink.tinkoff.controllers;


import com.maypink.tinkoff.models.Weather;
import com.maypink.tinkoff.services.WeatherServiceImpl;
import com.maypink.tinkoff.util.WeatherValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Controller
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
            return new ResponseEntity<>("Weather object is not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(weatherObject.get(0), HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Add new region",
            description = "Add new region with specified region name, temperature and date."
    )
    @PostMapping("/{regionName}")
    public ResponseEntity<?> add(@PathVariable @Parameter(description = "Region name") String regionName,
                                 @RequestParam(value="temperature") @Parameter(description = "Temperature") String temperature,
                                 @RequestParam(value="date") @Parameter(description = "Date") String date,
                                 BindingResult bindingResult){
        weatherValidator.validateStringParams(regionName, temperature, date, bindingResult);

        if (bindingResult.hasErrors()){
            return new ResponseEntity<>("Validation error", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        else {
            int parsedTemperature = Integer.parseInt(temperature);
            LocalDate parsedDate = LocalDate.parse(date);
            List<Weather> weatherList = weatherService.getWeatherByRegionNameAndDate(regionName, parsedDate);
            if (weatherList.isEmpty()) {
                Weather weather = weatherService.add(regionName, parsedTemperature, parsedDate);
                return new ResponseEntity<>(weather, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
    }

    @Operation(
            summary = "Update weather",
            description = "Update weather with specified region name, temperature and date"
    )
    @PutMapping("/{regionName}")
    public ResponseEntity<?> put(@PathVariable @Parameter(description = "Region name") String regionName,
                                 @RequestParam(value="temperature") @Parameter(description = "Temperature") String temperature,
                                 @RequestParam(value="date") @Parameter(description = "Date") String date,
                                 BindingResult bindingResult){
        weatherValidator.validateStringParams(regionName, temperature, date, bindingResult);

        if (bindingResult.hasErrors()){
            return new ResponseEntity<>("Validation error", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        else {
            int parsedTemperature = Integer.parseInt(temperature);
            LocalDate parsedDate = LocalDate.parse(date);
            Weather weather = weatherService.update(regionName, parsedTemperature, parsedDate);
            return new ResponseEntity<>(weather, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Delete all Weather object for specified region",
            description = "Delete all Weather object for specified region"
    )
    @DeleteMapping("/{regionName}")
    public ResponseEntity<?> delete(@PathVariable @Parameter(description = "Region name") String regionName,
                                    @RequestParam(value="date") @Parameter(description = "Date") String date,
                                    BindingResult bindingResult){

        weatherValidator.validateStringParams(regionName, date, bindingResult);

        if (bindingResult.hasErrors()){
            return new ResponseEntity<>("Validation error", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        else {
            LocalDate parsedDate = LocalDate.parse(date);
            Optional<List<Weather>> weatherList = weatherService.delete(regionName, parsedDate);
            if (weatherList.isPresent()) {
                return new ResponseEntity<>(weatherList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }
}
