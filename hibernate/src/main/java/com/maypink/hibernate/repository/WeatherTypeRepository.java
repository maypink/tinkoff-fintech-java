package com.maypink.hibernate.repository;

import com.maypink.hibernate.model.WeatherType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherTypeRepository extends JpaRepository<WeatherType, Long> {

    boolean existsById(Long id);
}
