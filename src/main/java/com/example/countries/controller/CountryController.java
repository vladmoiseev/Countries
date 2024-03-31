package com.example.countries.controller;

import com.example.countries.dto.CountryDto;
import com.example.countries.entity.Country;
import com.example.countries.exception.CountryAlreadyExistException;
import com.example.countries.exception.CountryNotFoundException;
import com.example.countries.service.CountryService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Controller class for handling country-related endpoints.
 */
@RestController
@RequestMapping("/countries")
public class CountryController {

  private static final String ERROR_MESSAGE = "Произошла ошибка!";
  private final CountryService countryService;
  private final Logger log = LoggerFactory.getLogger(CountryController.class);

  /**
   * Constructor for CountryController.
   *
   * @param countryService an instance of CountryService to handle country-related operations
   */
  public CountryController(CountryService countryService) {
    this.countryService = countryService;
  }

  /**
   * Endpoint to add a new country.
   *
   * @param country the country object to be added
   * @return ResponseEntity with success message if the country is added successfully, or error
   *     message if any exception occurs
   */
  @PostMapping
  public ResponseEntity<?> addCountry(@RequestBody Country country) {
    log.info("post-запрос для Country был вызван!");
    try {
      countryService.addCountry(country);
      log.info("Страна была успешно сохранена!");
      return ResponseEntity.ok("Страна была успешно сохранена!");
    } catch (CountryAlreadyExistException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(ERROR_MESSAGE);
    }
  }

  /**
   * Endpoint to retrieve a country by its name.
   *
   * @param name the name of the country to retrieve
   * @return ResponseEntity with the retrieved country if found, or error message if the country is
   *     not found or any exception occurs
   */
  @GetMapping
  public ResponseEntity<?> getCountry(@RequestParam(required = false) String name) {
    log.info("get_country-запрос для Country был вызван!");
    try {
      log.info("Страна была успешно получена!");
      return ResponseEntity.ok(countryService.getCountry(name));
    } catch (CountryNotFoundException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(ERROR_MESSAGE);
    }
  }

  /**
   * Endpoint to retrieve countries with a specific language.
   *
   * @param languageId the ID of the language
   * @return ResponseEntity with the list of countries with the specified language, or error message
   *     if any exception occurs
   */
  @GetMapping("/with-language")
  public ResponseEntity<?> getCountriesWithLanguage(@RequestParam Long languageId) {
    log.info("get_countries_with_language-запрос был вызван!");
    try {
      List<CountryDto> countries = countryService.getCountriesWithLanguage(languageId);
      log.info("Страна c языками была успешно получена!");
      return ResponseEntity.ok(countries);
    } catch (CountryNotFoundException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(ERROR_MESSAGE);
    }
  }

  /**
   * Endpoint to retrieve countries by name.
   *
   * @param name the name of the country
   * @return ResponseEntity with the list of countries with the specified name, or error message if
   *     any exception occurs
   */
  @GetMapping("/name")
  public ResponseEntity<?> getCountryByName(@RequestParam String name) {
    log.info("get_countries_by_name-запрос был вызван!");
    try {
      List<CountryDto> countries = countryService.getByCountryName(name);
      log.info("Страна была успешно получена!");
      return ResponseEntity.ok(countries);
    } catch (CountryNotFoundException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(ERROR_MESSAGE);
    }
  }

  /**
   * Endpoint to update an existing country.
   *
   * @param name          the name of the country to update
   * @param updatedCountry the updated country object
   * @return ResponseEntity with success message if the country is updated successfully, or error
   *     message if the country is not found or any exception occurs
   */
  @PutMapping
  public ResponseEntity<?> updateCountry(@RequestParam String name,
                                         @RequestBody Country updatedCountry) {
    log.info("put-запрос для Country был вызван!");
    try {
      countryService.updateCountry(name, updatedCountry);
      log.info("Страна была успешно изменена!");
      return ResponseEntity.ok("Страна была успешно изменена");
    } catch (CountryNotFoundException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(ERROR_MESSAGE);
    }
  }

  /**
   * Endpoint to delete an existing country by its ID.
   *
   * @param id the ID of the country to delete
   * @return ResponseEntity with success message if the country is deleted successfully,
   *     or error message if the country is not found or any exception occurs
   */
  @DeleteMapping
  public ResponseEntity<?> deleteCountry(@RequestParam Long id) {
    log.info("delete-запрос для Country был вызван!");
    try {
      countryService.deleteCountry(id);
      log.info("Страна была успешно удалена!");
      return ResponseEntity.ok("Страна была успешно удалена");
    } catch (CountryNotFoundException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(ERROR_MESSAGE);
    }
  }
}
