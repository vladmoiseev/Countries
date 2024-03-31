package com.example.countries.dto;

import com.example.countries.entity.City;

/**
 * Data Transfer Object (DTO) class representing city information.
 */
public class CityDto {
  private String name;

  /**
   * Converts a City entity to a CityDto model.
   *
   * @param entity the City entity to convert
   * @return the converted CityDto model
   */
  public static CityDto toModel(City entity) {
    CityDto model = new CityDto();
    model.setName(entity.getName());
    return model;
  }

  /**
   * Constructs a new CityDto instance.
   * No initialization logic needed for this constructor.
   */
  public CityDto() {
    // No initialization logic needed for this constructor
  }

  /**
   * Gets the name of the city.
   *
   * @return the name of the city
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the city.
   *
   * @param name the name of the city
   */
  public void setName(String name) {
    this.name = name;
  }
}
