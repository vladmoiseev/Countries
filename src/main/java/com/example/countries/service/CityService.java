package com.example.countries.service;

import com.example.countries.dto.CityDto;
import com.example.countries.entity.City;
import com.example.countries.entity.Country;
import com.example.countries.exception.CityAlreadyExistException;
import com.example.countries.exception.CityNotFoundException;
import com.example.countries.exception.CountryNotFoundException;
import com.example.countries.repository.CityRepository;
import com.example.countries.repository.CountryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing cities.
 */
@Service
public class CityService {
  private final CityRepository cityRepository;
  private final CountryRepository countryRepository;
  private static final String CITY_NOT_FOUND_STRING = "Город не найден!";

  @Autowired
  public CityService(CityRepository cityRepository, CountryRepository countryRepository) {
    this.cityRepository = cityRepository;
    this.countryRepository = countryRepository;
  }

  /**
   * Adds a new city to the specified country.
   *
   * @param id   the ID of the country to which the city belongs
   * @param city the city to add
   * @throws CountryNotFoundException     if the country with the specified ID is not found
   * @throws CityAlreadyExistException    if a city with the same name already exists in the country
   */
  public void addCity(Long id, City city)
      throws CountryNotFoundException, CityAlreadyExistException {
    Country country = countryRepository.findById(id).orElse(null);
    if (country != null) {
      city.setCountry(country);
      if (cityRepository.findByName(city.getName()) != null) {
        throw new CityAlreadyExistException("Такой город уже существует!");
      }
      cityRepository.save(city);
    } else {
      throw new CountryNotFoundException("Не удалось добавить город. Город не найден!");
    }
  }

  /**
   * Retrieves a city by its ID.
   *
   * @param id the ID of the city to retrieve
   * @return the DTO representation of the city
   * @throws CityNotFoundException if the city with the specified ID is not found
   */
  public CityDto getCity(Long id) throws CityNotFoundException {
    City cityEntity = cityRepository.findById(id).orElse(null);
    if (cityEntity != null) {
      return CityDto.toModel(cityEntity);
    } else {
      throw new CityNotFoundException(CITY_NOT_FOUND_STRING);
    }
  }

  /**
   * Updates an existing city.
   *
   * @param id   the ID of the city to update
   * @param city the updated city data
   * @throws CityNotFoundException if the city with the specified ID is not found
   */
  public void updateCity(Long id, City city) throws CityNotFoundException {
    City cityEntity = cityRepository.findById(id).orElse(null);
    if (cityEntity != null) {
      cityEntity.setName(city.getName());
      cityRepository.save(cityEntity);
    } else {
      throw new CityNotFoundException(CITY_NOT_FOUND_STRING);
    }
  }

  /**
   * Deletes a city by its ID.
   *
   * @param id the ID of the city to delete
   * @throws CityNotFoundException if the city with the specified ID is not found
   */
  @Transactional
  public void deleteCity(Long id) throws CityNotFoundException {
    City cityEntity = cityRepository.findById(id).orElse(null);
    if (cityEntity != null) {
      cityEntity.getCountry().getCityList().remove(cityEntity);
      countryRepository.save(cityEntity.getCountry());
      cityRepository.deleteById(id);
    } else {
      throw new CityNotFoundException(CITY_NOT_FOUND_STRING);
    }
  }

}
