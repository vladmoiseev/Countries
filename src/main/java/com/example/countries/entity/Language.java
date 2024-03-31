package com.example.countries.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a language.
 */
@Entity
public class Language {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "COUNTRY_LANGUAGE_MAPPING",
      joinColumns = @JoinColumn(name = "languageId"),
      inverseJoinColumns = @JoinColumn(name = "countryId"))
  private List<Country> countryList = new ArrayList<>();

  /**
   * Constructs a new Language instance.
   * No initialization logic needed for this constructor.
   */
  public Language() {
    // No initialization logic needed for this constructor
  }

  /**
   * Gets the ID of the language.
   *
   * @return the ID of the language
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the language.
   *
   * @param id the ID of the language
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the name of the language.
   *
   * @return the name of the language
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the language.
   *
   * @param name the name of the language
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the list of countries where the language is spoken.
   *
   * @return the list of countries where the language is spoken
   */
  public List<Country> getCountryList() {
    return countryList;
  }

  /**
   * Sets the list of countries where the language is spoken.
   *
   * @param countryList the list of countries where the language is spoken
   */
  public void setCountryList(List<Country> countryList) {
    this.countryList = countryList;
  }
}
