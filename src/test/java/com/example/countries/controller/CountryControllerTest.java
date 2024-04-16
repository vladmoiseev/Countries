package com.example.countries.controller;

import com.example.countries.dto.CountryDto;
import com.example.countries.entity.Country;
import com.example.countries.exception.CountryAlreadyExistException;
import com.example.countries.exception.CountryNotFoundException;
import com.example.countries.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CountryControllerTest {

  @Mock
  private CountryService countryService;

  @InjectMocks
  private CountryController countryController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void addCountry_ValidCountry_ReturnsSuccessResponse() throws CountryAlreadyExistException {
    Country country = new Country();
    doNothing().when(countryService).addCountry(any());

    ResponseEntity<?> response = countryController.addCountry(country);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Страна была успешно сохранена!", response.getBody());
  }

  @Test
  void addCountry_CountryAlreadyExists_ThrowsBadRequestException()
      throws CountryAlreadyExistException {
    Country country = new Country();
    doThrow(new CountryAlreadyExistException("Country already exists")).when(countryService).addCountry(any());

    HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
      countryController.addCountry(country);
    });

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
  }

  @Test
  void addCountriesBulk_ValidCountries_ReturnsSuccessResponse()
      throws CountryAlreadyExistException {
    List<Country> countries = Collections.singletonList(new Country());
    doNothing().when(countryService).addCountriesBulk(anyList());

    ResponseEntity<?> response = countryController.addCountriesBulk(countries);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Страны были успешно сохранены!", response.getBody());
  }

  // Similar tests for other methods

  @Test
  void getCountry_ValidName_ReturnsCountry() throws CountryNotFoundException {
    CountryDto countryDto = new CountryDto();
    when(countryService.getCountry(anyString())).thenReturn(countryDto);

    ResponseEntity<?> response = countryController.getCountry("CountryName");

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(countryDto, response.getBody());
  }

  @Test
  void getCountry_CountryNotFound_ThrowsBadRequestException() throws CountryNotFoundException {
    when(countryService.getCountry(anyString())).thenThrow(new CountryNotFoundException("Country not found"));

    HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
      countryController.getCountry("NonExistentCountry");
    });

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
  }

  @Test
  void getCountriesWithLanguage_ValidLanguageId_ReturnsListOfCountries()
      throws CountryNotFoundException {
    List<CountryDto> countryDtos = Collections.singletonList(new CountryDto());
    when(countryService.getCountriesWithLanguage(anyLong())).thenReturn(countryDtos);

    ResponseEntity<?> response = countryController.getCountriesWithLanguage(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(countryDtos, response.getBody());
  }

  @Test
  void deleteCountry_ValidId_ReturnsSuccessResponse() throws CountryNotFoundException {
    doNothing().when(countryService).deleteCountry(anyLong());

    ResponseEntity<?> response = countryController.deleteCountry(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Страна была успешно удалена", response.getBody());
  }
}
