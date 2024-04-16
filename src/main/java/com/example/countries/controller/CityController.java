package com.example.countries.controller;

import com.example.countries.entity.City;
import com.example.countries.exception.CityAlreadyExistException;
import com.example.countries.exception.CityNotFoundException;
import com.example.countries.exception.CountryNotFoundException;
import com.example.countries.service.CityService;
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
 * Controller class for handling city-related endpoints.
 */
@RestController
@RequestMapping("/cities")
public class CityController {

  private final CityService cityService;
  private final Logger log = LoggerFactory.getLogger(CityController.class);
  private static final String ERROR_MESSAGE = "Произошла ошибка!";

  /**
   * Constructor for CityController.
   *
   * @param cityService an instance of CityService to handle city-related operations
   */
  public CityController(CityService cityService) {
    this.cityService = cityService;
  }

  /**
   * Endpoint to add a new city.
   *
   * @param countryId the ID of the country to which the city belongs
   * @param city      the city object to be added
   * @return ResponseEntity with success message if the city is added successfully, or error message
   *     if any exception occurs
   */
  @PostMapping
  public ResponseEntity<String> addCity(@RequestParam Long countryId, @RequestBody City city) {
    log.info("post-запрос для City был вызван!");
    try {
      cityService.addCity(countryId, city);
      log.info("Город был успешно сохранен!");
      return ResponseEntity.ok("Город был успешно сохранен!");
    } catch (CountryNotFoundException | CityAlreadyExistException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(ERROR_MESSAGE);
    }
  }

  /**
   * Endpoint to retrieve a city by its ID.
   *
   * @param id the ID of the city to retrieve
   * @return ResponseEntity with the retrieved city if found, or error message if the city is not
   *     found or any exception occurs
   */
  @GetMapping
  public ResponseEntity<String> getCity(@RequestParam Long id) {
    log.info("get-запрос для City был вызван!");
    try {
      log.info("Город был успешно получен!");
      return ResponseEntity.ok(cityService.getCity(id));
    } catch (CityNotFoundException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(ERROR_MESSAGE);
    }
  }

  /**
   * Endpoint to update an existing city.
   *
   * @param id          the ID of the city to update
   * @param updatedCity the updated city object
   * @return ResponseEntity with success message if the city is updated successfully, or error
   *     message if the city is not found or any exception occurs
   */
  @PutMapping
  public ResponseEntity<String> updateCity(@RequestParam Long id, @RequestBody City updatedCity) {
    log.info("put-запрос для City был вызван!");
    try {
      cityService.updateCity(id, updatedCity);
      log.info("Город был успешно изменен!");
      return ResponseEntity.ok("Город был успешно изсенен!");
    } catch (CityNotFoundException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(ERROR_MESSAGE);
    }
  }

  /**
   * Endpoint to delete an existing city by its ID.
   *
   * @param id the ID of the city to delete
   * @return ResponseEntity with success message if the city is deleted successfully, or error
   *     message if the city is not found or any exception occurs
   */
  @DeleteMapping
  public ResponseEntity<String> deleteCity(@RequestParam Long id) {
    log.info("delete-запрос для City был вызван!");
    try {
      cityService.deleteCity(id);
      log.info("Город был успешно удален!");
      return ResponseEntity.ok("Город был успешно удален!");
    } catch (CityNotFoundException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(ERROR_MESSAGE);
    }
  }
}
