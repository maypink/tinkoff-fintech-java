package com.maypink.hibernate.repository;

import com.maypink.hibernate.model.WeatherType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherTypeRepository extends JpaRepository<WeatherType, Long> {

    public boolean exists(WeatherType weatherType);
}
