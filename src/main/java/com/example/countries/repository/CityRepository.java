package com.example.countries.repository;

import com.example.countries.entity.City;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing City entities.
 */
public interface CityRepository extends CrudRepository<City, Long> {

  /**
   * Finds a city by its name.
   *
   * @param name the name of the city to find
   * @return the city with the specified name, or null if not found
   */
  City findByName(String name);
}
