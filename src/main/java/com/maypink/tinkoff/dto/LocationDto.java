package com.maypink.tinkoff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LocationDto(
        String name,
        String region,
        String country,
        Double lat,
        Double lon,
        @JsonProperty("tz_id")
        String tzId,
        @JsonProperty("localtime_epoch")
        Integer localtimeEpoch,
        String localtime
) {
        public LocationDto(String name){
                this(name, "region", "country", 0D, 0D, "tzId", 0, "localTime");
        }
}
