package com.maypink.jdbc.repository;

import com.maypink.jdbc.dto.CityDto;
import com.maypink.jdbc.model.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository {

    public List<CityDto> getCityByName(String cityName);

    public void save(City city);
}
