package com.maypink.tinkoff.controllers;


import com.maypink.tinkoff.client.WeatherClient;
import com.maypink.tinkoff.dto.WeatherDto;
import com.maypink.tinkoff.models.Weather;
import com.maypink.tinkoff.services.WeatherServiceImpl;
import com.maypink.tinkoff.util.WeatherValidator;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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

    public WeatherController(WeatherServiceImpl weatherService, WeatherValidator weatherValidator) {
        this.weatherService = weatherService;
        this.weatherValidator = weatherValidator;
    }

    @Operation(
            summary = "Get weather",
            description = "Get weather object by region name and current date"
    )
    @GetMapping("/{regionName}")
    public ResponseEntity<?> get(@PathVariable @Parameter(description = "Region name") @NotEmpty @Size(max = 15) String regionName) {

        List<Weather> weathers = weatherService.getWeatherByRegionNameAndDate(regionName, LocalDate.now());
        if (weathers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Weather object is not found");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(weathers.get(0));
        }
    }

    @Operation(
            summary = "Add new region",
            description = "Add new region with specified region name, temperature and date."
    )
    @PostMapping("/new")
    public ResponseEntity<?> add(@RequestBody @Valid Weather weather, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation error");
        } else {
            List<Weather> weathers =
                    weatherService.getWeatherByRegionNameAndDate(weather.getRegionName(), weather.getDate());
            if (weathers.isEmpty()) {
                weatherService.add(weather);
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
    @PutMapping("/update")
    public ResponseEntity<?> put(@RequestBody @Valid Weather weather, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation error.");
        } else {
            weatherService.update(weather);
            return ResponseEntity.status(HttpStatus.OK).body(weather);
        }
    }

    @Operation(
            summary = "Delete all Weather object for specified region",
            description = "Delete all Weather object for specified region"
    )
    @DeleteMapping("/{regionName}")
    public ResponseEntity<?> delete(@PathVariable @Parameter(description = "Region name") String regionName,
                                    BindingResult bindingResult) {

        weatherValidator.validateStringParams(regionName, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation error.");
        } else {
            Optional<List<Weather>> weathers = weatherService.deleteByRegionName(regionName);
            if (weathers.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(weathers);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Weather with specified params was not found.");
            }
        }
    }

    @Operation(
            summary = "Get weather from Weather Service"
    )
    @GetMapping
    @RateLimiter(name = "rateLimiterApi")
    public ResponseEntity<WeatherDto> getWeather(@RequestParam String query) {
        return ResponseEntity.ok(weatherService.getWeather(query));
    }
}
