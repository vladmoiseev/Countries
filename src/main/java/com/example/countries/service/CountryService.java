package com.example.countries.service;

import com.example.countries.component.Cache;
import com.example.countries.dto.CountryDto;
import com.example.countries.entity.Country;
import com.example.countries.exception.CountryAlreadyExistException;
import com.example.countries.exception.CountryNotFoundException;
import com.example.countries.repository.CountryRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service class for managing countries.
 */
@Service
@Component
public class CountryService {
  private final ObjectMapper objectMapper;
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
    this.objectMapper = objectMapper;
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
   * Retrieves a list of countries by the country name.
   *
   * @param countryName the name of the country to search for
   * @return the list of DTO representations of countries
   * @throws CountryNotFoundException if no countries with the specified name are found
   */
  public List<CountryDto> getByCountryName(String countryName) throws CountryNotFoundException {
    List<CountryDto> cachedCountries = countryCache.getList(countryName);
    if (cachedCountries != null) {
      return cachedCountries;
    }

    String apiUrl = "https://restcountries.com/v3.1/name/"
        + URLEncoder.encode(countryName, StandardCharsets.UTF_8);

    RestTemplate restTemplate = new RestTemplate();

    try {
      String jsonString = restTemplate.getForObject(apiUrl, String.class);

      logger.info("JSON-ответ от {}: {}", apiUrl, jsonString);

      ObjectMapper mapper = new ObjectMapper();

      JsonNode jsonNode = mapper.readTree(jsonString);

      if (jsonNode.isArray() && jsonNode.size() > 0) {
        List<CountryDto> countries = new ArrayList<>();
        for (JsonNode countryNode : jsonNode) {
          Country country = new Country(countryNode);
          country.setCapital(countryNode.get("capital").get(0).asText());
          countries.add(CountryDto.toModel(country));
        }
        countryCache.putList(countryName, countries);
        return countries;
      } else {
        logger.warn("Получен пустой массив данных от запроса: {}", apiUrl);
        throw new CountryNotFoundException("Страна с таким названием не была найдена!");
      }
    } catch (Exception e) {
      logger.error("Ошибка при выполнении запроса!", e);
      throw new CountryNotFoundException("Произошла ошибка при выполнении запроса!");
    }
  }


  /**
   * Updates an existing country.
   *
   * @param name    the name of the country to update
   * @param country the updated country data
   * @throws CountryNotFoundException if the country with the specified name is not found
   */
  public void updateCountry(String name, Country country) throws CountryNotFoundException {
    Country countryEntity = countryRepository.findByName(name);
    if (countryEntity == null) {
      throw new CountryNotFoundException(COUNTRY_NOT_FOUND_STRING);
    }
    countryEntity.setName(country.getName());
    countryEntity.setCapital(country.getCapital());
    countryRepository.save(countryEntity);

    countryCache.clear();
  }

  /**
   * Deletes a country by its ID.
   *
   * @param id the ID of the country to delete
   * @throws CountryNotFoundException if the country with the specified ID is not found
   */
  public void deleteCountry(Long id) throws CountryNotFoundException {
    Country country = countryRepository.findById(id).orElse(null);
    if (country != null) {
      countryRepository.deleteById(id);

      countryCache.clear();
    } else {
      throw new CountryNotFoundException(COUNTRY_NOT_FOUND_STRING);
    }
  }
}
