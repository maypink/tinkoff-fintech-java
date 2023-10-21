package com.maypink.tinkoff.models;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class Weather {
    private UUID id;

    @NotEmpty
    @Size(max = 15)
    private String regionName;

    @Digits(integer = 3, fraction = 0)
    private Integer temperature;

    // no date in constructor to simplify process of creating new Weather objects
    public Weather(String regionName, int temperature) {
        this.id = UUID.nameUUIDFromBytes(regionName.getBytes());
        this.regionName = regionName;
        this.temperature = temperature;
    }

}
