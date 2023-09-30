package com.maypink.tinkoff.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class Weather {
    private UUID id;

    private String regionName;

    private Integer temperature;

    private LocalDate date;

    // no date in constructor to simplify process of creating new Weather objects
    public Weather(String regionName, int temperature) {
        this.id = UUID.nameUUIDFromBytes(regionName.getBytes());
        this.regionName = regionName;
        this.temperature = temperature;
        this.date = LocalDate.now();
    }

    public Weather(String regionName, int temperature, LocalDate date) {
        this.id = UUID.nameUUIDFromBytes(regionName.getBytes());
        this.regionName = regionName;
        this.temperature = temperature;
        this.date = date;
    }

}
