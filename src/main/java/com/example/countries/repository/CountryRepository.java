package com.example.countries.repository;

import com.example.countries.entity.Country;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing Country entities.
 */
public interface CountryRepository extends CrudRepository<Country, Long> {

  /**
   * Finds a country by its name.
   *
   * @param name the name of the country to find
   * @return the country with the specified name, or null if not found
   */
  Country findByName(String name);

  /**
   * Finds countries by the ID of a language in their language list.
   *
   * @param languageId the ID of the language to search for in the language list of countries
   * @return a list of countries having the specified language in their language list
   */
  @Query("SELECT d FROM Country d JOIN d.languageList i WHERE i.id = :languageId")
  List<Country> findCountriesByLanguageList_Id(Long languageId);
}
