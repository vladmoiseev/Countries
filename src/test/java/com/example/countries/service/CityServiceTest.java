package com.example.countries.service;

import com.example.countries.dto.CityDto;
import com.example.countries.entity.City;
import com.example.countries.entity.Country;
import com.example.countries.exception.CityAlreadyExistException;
import com.example.countries.exception.CityNotFoundException;
import com.example.countries.exception.CountryNotFoundException;
import com.example.countries.repository.CityRepository;
import com.example.countries.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CityServiceTest {

  @Mock
  private CityRepository cityRepository;

  @Mock
  private CountryRepository countryRepository;

  @InjectMocks
  private CityService cityService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testAddCity() throws CountryNotFoundException, CityAlreadyExistException {
    Country country = new Country();
    country.setId(1L);
    when(countryRepository.findById(1L)).thenReturn(Optional.of(country));

    City city = new City();
    city.setName("Test City");

    when(cityRepository.findByName("Test City")).thenReturn(null);

    cityService.addCity(1L, city);

    verify(cityRepository, times(1)).save(city);
  }

  @Test
  void testAddCityCityAlreadyExists() {
    Country country = new Country();
    country.setId(1L);
    when(countryRepository.findById(1L)).thenReturn(Optional.of(country));

    City existingCity = new City();
    existingCity.setName("Test City");
    when(cityRepository.findByName("Test City")).thenReturn(existingCity);

    City city = new City();
    city.setName("Test City");

    assertThrows(CityAlreadyExistException.class, () -> cityService.addCity(1L, city));

    verify(cityRepository, never()).save(city);
  }

  @Test
  void testAddCityCountryNotFound() {
    when(countryRepository.findById(1L)).thenReturn(Optional.empty());

    City city = new City();
    city.setName("Test City");

    assertThrows(CountryNotFoundException.class, () -> cityService.addCity(1L, city));

    verify(cityRepository, never()).save(city);
  }

  @Test
  void testGetCity() throws CityNotFoundException {
    City city = new City();
    city.setId(1L);
    city.setName("Test City");
    when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

    CityDto cityDto = cityService.getCity(1L);

    assertNotNull(cityDto);
    assertEquals("Test City", cityDto.getName());
  }

  @Test
  void testGetCityNotFound() {
    when(cityRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(CityNotFoundException.class, () -> cityService.getCity(1L));
  }

  @Test
  void testUpdateCity() throws CityNotFoundException {
    City city = new City();
    city.setId(1L);
    city.setName("Old City Name");
    when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

    City updatedCity = new City();
    updatedCity.setName("New City Name");

    cityService.updateCity(1L, updatedCity);

    assertEquals("New City Name", city.getName());
    verify(cityRepository, times(1)).save(city);
  }

  @Test
  void testUpdateCityNotFound() {
    when(cityRepository.findById(1L)).thenReturn(Optional.empty());

    City updatedCity = new City();
    updatedCity.setName("New City Name");

    assertThrows(CityNotFoundException.class, () -> cityService.updateCity(1L, updatedCity));
  }

  @Test
  void testDeleteCity() throws CityNotFoundException {
    City city = new City();
    city.setId(1L);
    Country country = new Country();
    country.setId(1L);
    city.setCountry(country);
    when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

    cityService.deleteCity(1L);

    verify(cityRepository, times(1)).deleteById(1L);
    verify(countryRepository, times(1)).save(country);
    assertTrue(country.getCityList().isEmpty());
  }

  @Test
  void testDeleteCityNotFound() {
    when(cityRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(CityNotFoundException.class, () -> cityService.deleteCity(1L));
  }
}
