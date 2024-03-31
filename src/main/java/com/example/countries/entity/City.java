package com.example.countries.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Entity class representing a city.
 */
@Entity
public class City {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;

  @ManyToOne
  @JoinColumn(name = "countryId")
  private Country country;

  /**
   * Constructs a new City instance.
   * No initialization logic needed for this constructor.
   */
  public City() {
    // No initialization logic needed for this constructor
  }

  /**
   * Gets the ID of the city.
   *
   * @return the ID of the city
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the city.
   *
   * @param id the ID of the city
   */
  public void setId(Long id) {
    this.id = id;
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

  /**
   * Gets the country to which the city belongs.
   *
   * @return the country to which the city belongs
   */
  public Country getCountry() {
    return country;
  }

  /**
   * Sets the country to which the city belongs.
   *
   * @param country the country to which the city belongs
   */
  public void setCountry(Country country) {
    this.country = country;
  }
}
