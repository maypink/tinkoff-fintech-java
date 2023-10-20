package com.maypink.jdbc.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class CityWeatherDto {
    Long id;
    Long cityId;
    Long weatherTypeId;
}
