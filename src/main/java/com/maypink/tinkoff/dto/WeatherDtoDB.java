package com.maypink.tinkoff.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class WeatherDtoDB{
    Long id;
    String name;
    String region;
    String country;
    Integer tempC;
    Double tempF;

    String dateTime;
}
