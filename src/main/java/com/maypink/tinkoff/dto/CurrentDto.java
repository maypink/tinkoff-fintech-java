package com.maypink.tinkoff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

public record CurrentDto(
        @JsonProperty("last_updated_epoch")
        Integer lastUpdatedEpoch,
        @JsonProperty("last_updated")
        String lastUpdated,
        @JsonProperty("temp_c")
        Integer tempC,
        @JsonProperty("temp_f")
        Double tempF,
        @JsonProperty("is_day")
        Integer isDay,
        ConditionDto condition,
        @JsonProperty("wind_mph")
        Double windMph,
        @JsonProperty("wind_kph")
        Double windKph,
        @JsonProperty("wind_degree")
        Integer windDegree,
        @JsonProperty("wind_dir")
        String windDir,
        @JsonProperty("pressure_mb")
        Integer pressureMb,
        @JsonProperty("pressure_in")
        Double pressureIn,
        @JsonProperty("precip_mm")
        Integer preCipMm,
        @JsonProperty("precip_in")
        Integer preCipIn,
        Integer humidity,
        Integer cloud,
        @JsonProperty("feelslike_c")
        Integer feelsLikeC,
        @JsonProperty("feelslike_f")
        Double feelsLikeF,
        @JsonProperty("vis_km")
        Integer visKm,
        @JsonProperty("vis_miles")
        Integer visMiles,
        Integer uv,
        @JsonProperty("gust_mph")
        Double gustMph,
        @JsonProperty("gust_kph")
        Integer gustKph
) {
        public CurrentDto(){
                this(0, "lastUpdated", 0, 0D, 0,
                        new ConditionDto("text", "icon", 0),
                        0D, 0D, 0, "windDir", 0, 0D, 0, 0, 0, 0, 0, 0D, 0, 0, 0, 0D, 0);
        }
}
