package com.example.countries.dto;

import com.example.countries.entity.Country;

/**
 * Data Transfer Object (DTO) class representing country information.
 */
public class CountryDto {
  private String name;
  private String capital;

  /**
   * Converts a Country entity to a CountryDto model.
   *
   * @param entity the Country entity to convert
   * @return the converted CountryDto model
   */
  public static CountryDto toModel(Country entity) {
    CountryDto model = new CountryDto();
    model.setName(entity.getName());
    model.setCapital(entity.getCapital());
    return model;
  }

  /**
   * Constructs a new CountryDto instance.
   * No initialization logic needed for this constructor.
   */
  public CountryDto() {
    // No initialization logic needed for this constructor
  }

  /**
   * Gets the name of the country.
   *
   * @return the name of the country
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the country.
   *
   * @param name the name of the country
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the capital of the country.
   *
   * @return the capital of the country
   */
  public String getCapital() {
    return capital;
  }

  /**
   * Sets the capital of the country.
   *
   * @param capital the capital of the country
   */
  public void setCapital(String capital) {
    this.capital = capital;
  }
}
