package com.example.countries.service;

import com.example.countries.component.Cache;
import com.example.countries.dto.CountryDto;
import com.example.countries.entity.Country;
import com.example.countries.exception.CountryAlreadyExistException;
import com.example.countries.exception.CountryNotFoundException;
import com.example.countries.repository.CountryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class for managing countries.
 */
@Service
@Component
public class CountryService {
  private final CountryRepository countryRepository;

  private final Cache<String, CountryDto> countryCache;
  private static final Logger logger = LoggerFactory.getLogger(CountryService.class);
  private static final String COUNTRY_NOT_FOUND_STRING = "Страна не найдена";

  /**
   * Аннотация, которая позволяет Spring автоматически проводить внедрение (инъекцию) зависимостей
   * в бины.
   * Внедрение зависимостей осуществляется на основе типов данных.
   */
  @Autowired
  public CountryService(ObjectMapper objectMapper, CountryRepository countryRepository,
                        Cache<String, CountryDto> countryCache) {
    this.countryRepository = countryRepository;
    this.countryCache = countryCache;
  }

  /**
   * Adds a new country.
   *
   * @param country the country to add
   * @throws CountryAlreadyExistException if a country with the same name already exists
   */
  public void addCountry(Country country) throws CountryAlreadyExistException {
    if (countryRepository.findByName(country.getName()) != null) {
      throw new CountryAlreadyExistException("Такая страна уже существует!");
    }
    countryRepository.save(country);

    countryCache.clear();
  }

  /**
   * Добавляет список стран в базу данных, игнорируя те, которые уже существуют.
   *
   * @param countries Список стран для добавления
   * @throws CountryAlreadyExistException Если некоторые страны уже существуют в базе данных
   */
  public void addCountriesBulk(List<Country> countries) throws CountryAlreadyExistException {
    Set<String> existingCountryNames = new HashSet<>();
    for (Country country : countryRepository.findAll()) {
      existingCountryNames.add(country.getName());
    }

    List<Country> newCountries = new ArrayList<>(countries.stream()
        .filter(country -> !existingCountryNames.contains(country.getName()))
        .collect(Collectors.toMap(Country::getName, country -> country,
            (country1, country2) -> country1))
        .values());

    List<Country> savedCountries = new ArrayList<>();
    countryRepository.saveAll(newCountries).forEach(savedCountries::add);
    int countSavedCountries = savedCountries.size();

    if (countSavedCountries == 0) {
      throw new CountryAlreadyExistException("Все страны из списка уже есть в базе.");
    } else if (newCountries.size() == countSavedCountries) {
      logger.info("Успешно добавлено {} новых стран.", countSavedCountries);
    } else {
      logger.error("Ошибка при добавлении новых стран. Добавлено {} из {} стран.", countSavedCountries, newCountries.size());
      throw new RuntimeException("Ошибка при добавлении новых стран.");
    }
  }

  /**
   * Retrieves a country by its name.
   *
   * @param name the name of the country to retrieve
   * @return the DTO representation of the country
   * @throws CountryNotFoundException if the country with the specified name is not found
   */
  public CountryDto getCountry(String name) throws CountryNotFoundException {
    if (countryCache.containsKey(name)) {
      return countryCache.get(name);
    } else {
      Country country = countryRepository.findByName(name);
      if (country == null) {
        throw new CountryNotFoundException(COUNTRY_NOT_FOUND_STRING);
      }
      CountryDto countryDto = CountryDto.toModel(country);
      countryCache.put(name, countryDto);
      return countryDto;
    }
  }

  /**
   * Retrieves a list of countries with the specified language.
   *
   * @param languageId the ID of the language
   * @return the list of DTO representations of countries
   * @throws CountryNotFoundException if no countries with the specified language are found
   */
  public List<CountryDto> getCountriesWithLanguage(Long languageId)
      throws CountryNotFoundException {
    String cacheKey = "language_" + languageId;
    if (countryCache.containsKey(cacheKey)) {
      return countryCache.getList(cacheKey);
    } else {
      List<Country> countries = countryRepository.findCountriesByLanguageList_Id(languageId);
      if (countries.isEmpty()) {
        throw new CountryNotFoundException(COUNTRY_NOT_FOUND_STRING);
      }
      List<CountryDto> countryDtos = countries.stream().map(CountryDto::toModel).toList();
      countryCache.putList(cacheKey, countryDtos);
      return countryDtos;
    }
  }

  /**
   * Updates an existing country.
   *
   * @param name    the name of the country to update
   * @param country the updated country data
   * @throws CountryNotFoundException if the country with the specified name is not found
   */
  public CountryDto updateCountry(String name, Country country) throws CountryNotFoundException {
    Country countryEntity = countryRepository.findByName(name);
    if (countryEntity == null) {
      throw new CountryNotFoundException(COUNTRY_NOT_FOUND_STRING);
    }
    countryEntity.setName(country.getName());
    countryEntity.setCapital(country.getCapital());
    countryRepository.save(countryEntity);

    countryCache.clear();
    return null;
  }

  public void deleteCountryByName(String name) throws CountryNotFoundException {
    Country country = countryRepository.findByName(name);
    if (country != null) {
      countryRepository.delete(country);
      countryCache.clear();
    } else {
      throw new CountryNotFoundException(COUNTRY_NOT_FOUND_STRING);
    }
  }
}
