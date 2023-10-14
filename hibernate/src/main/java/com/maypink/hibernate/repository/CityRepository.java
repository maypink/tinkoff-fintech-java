package com.maypink.hibernate.repository;

import com.maypink.hibernate.model.City;;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

    public List<City> getCityByName(String cityName);
}
