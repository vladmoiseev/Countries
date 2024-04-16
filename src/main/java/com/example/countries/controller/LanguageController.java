package com.example.countries.controller;

import com.example.countries.entity.Language;
import com.example.countries.exception.CountryNotFoundException;
import com.example.countries.exception.LanguageAlreadyExistException;
import com.example.countries.exception.LanguageNotFoundException;
import com.example.countries.service.LanguageService;
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
 * Controller class for handling language-related endpoints.
 */
@RestController
@RequestMapping("/languages")
public class LanguageController {

  private final LanguageService languageService;
  private final Logger log = LoggerFactory.getLogger(LanguageController.class);
  private static final String ERROR_MESSAGE = "Произошла ошибка!";

  /**
   * Constructor for LanguageController.
   *
   * @param languageService an instance of LanguageService to handle language-related operations
   */
  public LanguageController(LanguageService languageService) {
    this.languageService = languageService;
  }

  /**
   * Endpoint to add a new language.
   *
   * @param countryId the ID of the country to which the language belongs
   * @param language  the language object to be added
   * @return ResponseEntity with success message if the language is added successfully, or error
   *     message if any exception occurs
   */
  @PostMapping
  public ResponseEntity<String> addLanguage(@RequestParam Long countryId,
                                       @RequestBody Language language) {
    log.info("post-запрос для Language был вызван!");
    try {
      languageService.addLanguage(countryId, language);
      log.info("Язык был успешно сохранен!");
      return ResponseEntity.ok("Язык был успешно сохранен!");
    } catch (CountryNotFoundException | LanguageAlreadyExistException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(ERROR_MESSAGE);
    }
  }

  /**
   * Endpoint to retrieve a language by its ID.
   *
   * @param id the ID of the language to retrieve
   * @return ResponseEntity with the retrieved language if found, or error message if the language
   *     is not found or any exception occurs
   */
  @GetMapping
  public ResponseEntity<String> getLanguage(@RequestParam Long id) {
    log.info("get-запрос для Language был вызван!");
    try {
      log.info("Язык был успешно получен!");
      return ResponseEntity.ok(languageService.getLanguage(id));
    } catch (LanguageNotFoundException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(ERROR_MESSAGE);
    }
  }

  /**
   * Endpoint to update an existing language.
   *
   * @param id             the ID of the language to update
   * @param updatedLanguage the updated language object
   * @return ResponseEntity with success message if the language is updated successfully, or error
   *     message if the language is not found or any exception occurs
   */
  @PutMapping
  public ResponseEntity<String> updateLanguage(@RequestParam Long id,
                                          @RequestBody Language updatedLanguage) {
    log.info("put-запрос для Language был вызван!");
    try {
      languageService.updateLanguage(id, updatedLanguage);
      log.info("Язык был успешно изменен!");
      return ResponseEntity.ok("Язык был успешно изменен!");
    } catch (LanguageNotFoundException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(ERROR_MESSAGE);
    }
  }

  /**
   * Endpoint to delete an existing language by its ID.
   *
   * @param countryId  the ID of the country from which to delete the language
   * @param languageId the ID of the language to delete
   * @return ResponseEntity with success message if the language is deleted successfully, or error
   *     message if the language is not found or any exception occurs
   */
  @DeleteMapping
  public ResponseEntity<String> deleteLanguage(@RequestParam Long countryId,
                                          @RequestParam Long languageId) {
    log.info("delete-запрос для Language был вызван!");
    try {
      languageService.deleteLanguage(countryId, languageId);
      log.info("Язык был успешно удален!");
      return ResponseEntity.ok("Язык был успешно удален!");
    } catch (LanguageNotFoundException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(ERROR_MESSAGE);
    }
  }
}
