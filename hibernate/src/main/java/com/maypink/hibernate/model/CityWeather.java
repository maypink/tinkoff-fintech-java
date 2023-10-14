package com.maypink.hibernate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CityWeather")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityWeather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "CityId")
    @ManyToOne
    private City city;
    @JoinColumn(name = "WeatherTypeId")
    @ManyToOne
    private WeatherType weatherType;
}
