package com.maypink.tinkoff.controllers;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherServiceImpl weatherService;
    private final WeatherMapper weatherMapper;

    @Operation(
            summary = "Get weather from database."
    )
    @GetMapping("/{name}")
    @PreAuthorize("hasAuthority('weather:read')")
    public ResponseEntity<?> get(@PathVariable @Parameter(description = "name") @NotEmpty @Size(max = 15) String name) {

        List<WeatherResource> weatherResources = weatherService.getWeatherByName(name);
        if (weatherResources.isEmpty()){
            throw new WeatherNotFoundException("Weather with specified name not found");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(weatherResources.get(0));
        }
    }

    @Operation(
            summary = "Get all weathers from database."
    )
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('weather:read')")
    public ResponseEntity<?> getAllWeathers() {
        List<WeatherResource> weatherResources = weatherService.getAllWeathers();
        return ResponseEntity.status(HttpStatus.OK).body(weatherResources);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Operation(
            summary = "Add weather from Weather Service"
    )
    @PostMapping("/new")
    @PreAuthorize("hasAuthority('weather:write')")
    public ResponseEntity<?> add(@RequestBody @Valid String query) throws ResponseWeatherErrorException {

        WeatherResource weatherResource = weatherMapper.toResource(weatherService.getWeather(query));
        weatherService.add(weatherResource);
        return ResponseEntity.status(HttpStatus.CREATED).body(weatherResource);
    }
}
