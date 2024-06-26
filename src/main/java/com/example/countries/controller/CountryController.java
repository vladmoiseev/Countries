package com.example.countries.controller;

import com.example.countries.dto.CountryDto;
import com.example.countries.entity.Country;
import com.example.countries.exception.CountryAlreadyExistException;
import com.example.countries.exception.CountryNotFoundException;
import com.example.countries.service.CountryService;
import com.example.countries.service.RequestCounterService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  final RequestCounterService requestCounterService;
  private final Logger log = LoggerFactory.getLogger(CountryController.class);

  /**
   * Constructor for CountryController.
   *
   * @param countryService an instance of CountryService to handle country-related operations
   */
  public CountryController(CountryService countryService, RequestCounterService requestCounterService) {
    this.countryService = countryService;
    this.requestCounterService = requestCounterService;
  }

  /**
   * Endpoint to add a new country.
   *
   * @param country the country object to be added
   * @return ResponseEntity with success message if the country is added successfully, or error
   *     message if any exception occurs
   */
  @PostMapping
  @CrossOrigin
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
   * Обрабатывает POST запрос для добавления стран в большом количестве.
   *
   * @param countries Список стран для добавления
   * @return ResponseEntity с информацией о статусе операции
   */
  @PostMapping("/bulk")
  @CrossOrigin
  public ResponseEntity<?> addCountriesBulk(@RequestBody List<Country> countries) {
    log.info("post-запрос для Country был вызван!");
    try {
      countryService.addCountriesBulk(countries);
      log.info("Страны были успешно сохранены!");
      return ResponseEntity.ok("Страны были успешно сохранены!");
    } catch (CountryAlreadyExistException e) {
      log.warn(e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      log.error("Произошла ошибка при добавлении стран", e);
      return ResponseEntity.badRequest().body(ERROR_MESSAGE);
    }
  }

  /**
   * Endpoint to retrieve a country by its name.
   *
   * @param name the name of the country to retrieve
   * @return ResponseEntity with the retrieved country if found, or error message if the country is
   * not found or any exception occurs
   */
  @GetMapping
  @CrossOrigin
  public ResponseEntity<?> getCountry(@RequestParam(required = false) String name) {
    log.info("get_country-запрос для Country был вызван!");
    try {
      requestCounterService.increment();
      log.info("Страна была успешно получена!");
      return ResponseEntity.ok(countryService.getCountry(name));
    } catch (CountryNotFoundException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(ERROR_MESSAGE);
    }
  }

  @GetMapping("/getRequestCount")
  public String getRequestCount() {
    int requestCount = requestCounterService.getCount();
    return "Total number of requests: " + requestCount;
  }

  /**
   * Endpoint to retrieve countries with a specific language.
   *
   * @param languageId the ID of the language
   * @return ResponseEntity with the list of countries with the specified language, or error message
   *     if any exception occurs
   */
  @GetMapping("/with-language")
  @CrossOrigin
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
   * Endpoint to update an existing country.
   *
   * @param name          the name of the country to update
   * @param updatedCountry the updated country object
   * @return ResponseEntity with success message if the country is updated successfully, or error
   *     message if the country is not found or any exception occurs
   */
  @PutMapping
  @CrossOrigin
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

  @DeleteMapping
  @CrossOrigin
  public ResponseEntity<?> deleteCountry(@RequestParam String name) {
    log.info("delete-запрос для Country был вызван!");
    try {
      countryService.deleteCountryByName(name);
      log.info("Страна была успешно удалена!");
      return ResponseEntity.ok("Страна была успешно удалена");
    } catch (CountryNotFoundException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(ERROR_MESSAGE);
    }
  }
}
