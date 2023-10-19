package com.maypink.jdbc.controller;

import com.maypink.jdbc.dto.CityWeatherDto;
import com.maypink.jdbc.model.CityWeather;
import com.maypink.jdbc.service.CityWeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weatherJdbc")
@RequiredArgsConstructor
public class WeatherController {
    private final CityWeatherService cityWeatherService;

    @Operation(
            summary = "Add new CityWeather",
            description = "Add new CityWeather."
    )
    @PostMapping("/new")
    public ResponseEntity<?> add(@RequestBody @Valid CityWeather cityWeather, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation error");
        } else {
            cityWeatherService.save(cityWeather);
            return ResponseEntity.status(HttpStatus.CREATED).body(cityWeather);
        }
    }

    @Operation(
            summary = "Get CityWeather",
            description = "Get CityWeather object by city name."
    )
    @GetMapping("/{cityName}")
    public ResponseEntity<?> get(@PathVariable @Parameter(description = "cityName") @NotEmpty @Size(max = 20) String cityName) {

        List<CityWeatherDto> cityWeathersDtos = cityWeatherService.getCityWeathersByCityName(cityName);
        return ResponseEntity.status(HttpStatus.OK).body(cityWeathersDtos.get(0));
    }

    @Operation(
            summary = "Update cityWeather",
            description = "Update cityWeather with specified weatherType."
    )
    @PutMapping("/update")
    public ResponseEntity<?> put(@RequestBody @Valid CityWeather cityWeather, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation error.");
        } else {
            cityWeatherService.update(cityWeather);
            return ResponseEntity.status(HttpStatus.OK).body(cityWeather);
        }
    }

    @Operation(
            summary = "Delete all CityWeather objects with specified City.",
            description = "Delete all CityWeather objects with specified City."
    )
    @DeleteMapping("/{cityName}")
    public ResponseEntity<?> delete(@PathVariable @Parameter(description = "cityName") @NotEmpty @Size(max = 20) String cityName) {
        CityWeatherDto cityWeatherDto = cityWeatherService.deleteByCityName(cityName);
        return ResponseEntity.status(HttpStatus.OK).body(cityWeatherDto);

    }
}
