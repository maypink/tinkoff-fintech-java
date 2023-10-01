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
    private final List<Weather> weathers = createListWeather();

    public List<Weather> getWeatherByRegionAndDate(String regionName, LocalDate date){
        return weathers.stream()
                .filter(r -> r.getRegionName().equals(regionName) && r.getDate().equals(date))
                .toList();
    }

    public Weather addWeather(Weather weather){
        weathers.add(weather);
        return weather;
    }

    public Weather updateWeatherWithTemperature(Weather weatherToUpdate, Integer temperature){
        weatherToUpdate.setTemperature(temperature);
        return weatherToUpdate;
    }

    public Optional<List<Weather>> deleteWeather(String regionName){
        Predicate<Weather> filter = r -> r.getRegionName().equals(regionName);
        List<Weather> weatherForDelete = weathers
                .stream()
                .filter(filter)
                .toList();
        if (weatherForDelete.isEmpty()){
            return Optional.empty();
        }
        weathers.removeIf(filter);
        return Optional.of(weatherForDelete);
    }

    private static List<Weather> createListWeather(){
        Weather moscowWeather = new Weather("Moscow", -40);
        Weather surgutWeather = new Weather("Surgut", -10);
        Weather saintPeterburgWeather = new Weather("SP", 15);
        Weather berlinWeather = new Weather("Berlin", 20);
        Weather darmstadtWeather = new Weather("Darmstadt", 17);
        Weather munichWeather = new Weather("Munich", 15);

        List<Weather> regions = new ArrayList<>();
        regions.add(moscowWeather);
        regions.add(surgutWeather);
        regions.add(saintPeterburgWeather);
        regions.add(berlinWeather);
        regions.add(darmstadtWeather);
        regions.add(munichWeather);
        return regions;
    }
}
