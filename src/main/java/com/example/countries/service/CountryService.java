package com.example.countries.service;

import com.example.countries.entity.Country;
import com.example.countries.exception.CountryAlreadyExistException;
import com.example.countries.exception.CountryNotFoundException;
import com.example.countries.dto.CountryDTO;
import com.example.countries.repository.CountryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CountryService {

    private final CountryRepository countryRepository;
    private static final Logger logger = LoggerFactory.getLogger(CountryService.class);
    private static final String COUNTRY_NOT_FOUND_STRING = "Страна не найдена";

    @Autowired
    public CountryService(ObjectMapper objectMapper, CountryRepository countryRepository){
        this.countryRepository = countryRepository;
    }

    public void addCountry(Country country) throws CountryAlreadyExistException {
        if (countryRepository.findByName(country.getName()) != null) {
            throw new CountryAlreadyExistException("Такая страна уже существует!");
        }
        countryRepository.save(country);
    }

    public CountryDTO getCountry(String name) throws CountryNotFoundException {
        Country country = countryRepository.findByName(name);
        if (country == null) {
            throw new CountryNotFoundException(COUNTRY_NOT_FOUND_STRING);
        }
        return CountryDTO.toModel(country);
    }


    public List<CountryDTO> getByCountryName(String countryName) throws CountryNotFoundException {
        String apiUrl = "https://restcountries.com/v3.1/name/" + URLEncoder.encode(countryName, StandardCharsets.UTF_8);

        RestTemplate restTemplate = new RestTemplate();

        try {
            String jsonString = restTemplate.getForObject(apiUrl, String.class);

            logger.info("JSON-ответ от {}: {}", apiUrl, jsonString);

            ObjectMapper mapper = new ObjectMapper();

            JsonNode jsonNode = mapper.readTree(jsonString);

            if (jsonNode.isArray() && jsonNode.size() > 0) {
                List<CountryDTO> countries = new ArrayList<>();
                for (JsonNode countryNode : jsonNode) {
                    Country country = new Country(countryNode);
                    country.setCapital(countryNode.get("capital").get(0).asText());
                    countries.add(CountryDTO.toModel(country));
                }
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

    public void updateCountry(String name, Country country) throws CountryNotFoundException {
        Country countryEntity = countryRepository.findByName(name);
        if (countryEntity == null) {
            throw new CountryNotFoundException(COUNTRY_NOT_FOUND_STRING);
        }
        countryEntity.setName(country.getName());
        countryEntity.setCapital(country.getCapital());
        countryRepository.save(countryEntity);
    }

    public void deleteCountry(Long id) throws CountryNotFoundException {
        Country country = countryRepository.findById(id).orElse(null);
        if (country != null) {
            countryRepository.deleteById(id);
        } else {
            throw new CountryNotFoundException(COUNTRY_NOT_FOUND_STRING);
        }
    }
}