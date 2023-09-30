package com.maypink.tinkoff.repositories;

import com.maypink.tinkoff.models.Weather;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Repository
public class WeatherRepository {
    private final List<Weather> weatherList = createListWeather();

    public List<Weather> getWeatherByRegionAndDate(String regionName, LocalDate date){
        return weatherList.stream()
                .filter(r -> r.getRegionName().equals(regionName))
                .filter(r -> r.getDate().equals(date))
                .toList();
    }

    public Weather addWeather(String regionName, Integer temperature, LocalDate date){
        List<Weather> filteredWeatherList = this.getWeatherByRegionAndDate(regionName, date);

        // if there is no such Weather object
        if (filteredWeatherList.isEmpty()) {
            return this.addWeather(regionName, temperature, date);
        }

        Weather weather = new Weather(regionName, temperature, date);
        weatherList.add(weather);
        return weather;
    }

    public Weather updateWeather(String regionName, Integer temperature, LocalDate date){
        List<Weather> weatherList = this.getWeatherByRegionAndDate(regionName, date);

        // if there is no such Weather object
        if (weatherList.isEmpty()) {
            return this.addWeather(regionName, temperature, date);
        } else {
            // there must be only zero or one weather with specified date and region, since adding new object with the same data
            // is impossible
            Weather weather = weatherList.get(0);
            weather.setTemperature(temperature);
            return weather;
        }
    }

    public Optional<List<Weather>> deleteWeather(String regionName){
        Predicate<Weather> filter = r -> r.getRegionName().equals(regionName);
        List<Weather> weatherListDeleted = weatherList
                .stream()
                .filter(filter)
                .toList();
        if (weatherListDeleted.isEmpty()){
            return Optional.empty();
        }
        weatherList.removeIf(filter);
        return Optional.of(weatherListDeleted);
    }

    private static List<Weather> createListWeather(){
        Weather moscowWeather = new Weather("Moscow", -40);
        Weather surgutWeather = new Weather("Surgut", -10);
        Weather surgutWeather2 = new Weather("Surgut", -20);
        Weather saintPeterburgWeather = new Weather("SP", 15);
        Weather berlinWeather = new Weather("Berlin", 20);
        Weather darmstadtWeather = new Weather("Darmstadt", 17);
        Weather munichWeather = new Weather("Munich", 15);

        List<Weather> regions = new ArrayList<>();
        regions.add(moscowWeather);
        regions.add(surgutWeather);
        regions.add(surgutWeather2);
        regions.add(saintPeterburgWeather);
        regions.add(berlinWeather);
        regions.add(darmstadtWeather);
        regions.add(munichWeather);
        return regions;
    }
}
