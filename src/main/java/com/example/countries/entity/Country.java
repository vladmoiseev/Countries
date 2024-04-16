package com.example.countries.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a country.
 */
@Entity
public class Country {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private String capital;

  @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
      CascadeType.REMOVE}, mappedBy = "country")
  private List<City> cityList = new ArrayList<>();

  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(name = "COUNTRY_LANGUAGE_MAPPING",
      joinColumns = @JoinColumn(name = "countryId"),
      inverseJoinColumns = @JoinColumn(name = "languageId"))
  private List<Language> languageList = new ArrayList<>();

  /**
   * Constructs a new Country instance.
   * No initialization logic needed for this constructor.
   */
  public Country() {
    // No initialization logic needed for this constructor
  }

  public Country(String name) {
    this.name = name;
  }


  /**
   * Constructs a new Country instance from a JSON node.
   *
   * @param jsonNode the JSON node representing country data
   */
  public Country(JsonNode jsonNode) {
    this.name = jsonNode.get("name").get("common").asText();
    JsonNode capitalNode = jsonNode.get("capital");
    this.capital = (capitalNode != null && !capitalNode.isNull()) ? capitalNode.asText() : null;
  }

  /**
   * Gets the ID of the country.
   *
   * @return the ID of the country
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the country.
   *
   * @param id the ID of the country
   */
  public void setId(Long id) {
    this.id = id;
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
   * Gets the capital city of the country.
   *
   * @return the capital city of the country
   */
  public String getCapital() {
    return capital;
  }

  /**
   * Sets the capital city of the country.
   *
   * @param capital the capital city of the country
   */
  public void setCapital(String capital) {
    this.capital = capital;
  }

  /**
   * Gets the list of cities in the country.
   *
   * @return the list of cities in the country
   */
  public List<City> getCityList() {
    return cityList;
  }

  /**
   * Sets the list of cities in the country.
   *
   * @param cityList the list of cities in the country
   */
  public void setCityList(List<City> cityList) {
    this.cityList = cityList;
  }

  /**
   * Gets the list of languages spoken in the country.
   *
   * @return the list of languages spoken in the country
   */
  public List<Language> getLanguageList() {
    return languageList;
  }

  /**
   * Sets the list of languages spoken in the country.
   *
   * @param languageList the list of languages spoken in the country
   */
  public void setLanguageList(List<Language> languageList) {
    this.languageList = languageList;
  }
}
