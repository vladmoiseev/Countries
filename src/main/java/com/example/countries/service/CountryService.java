package com.example.countries.service;

import com.example.countries.entity.CountryEntity;
import com.example.countries.exception.CountryAlreadyExistException;
import com.example.countries.exception.CountryNotFoundException;
import com.example.countries.model.Country;
import com.example.countries.repository.CountryRepo;
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

    private final CountryRepo countryRepo;
    private static final Logger logger = LoggerFactory.getLogger(CountryService.class);

    @Autowired
    public CountryService(ObjectMapper objectMapper, CountryRepo countryRepo){
        this.countryRepo = countryRepo;
    }

    public CountryEntity add(CountryEntity country) throws CountryAlreadyExistException {
        List<CountryEntity> countries = countryRepo.findByName(country.getName());
        if (!countries.isEmpty()) {
            throw new CountryAlreadyExistException("Страна с таким название уже существует!");
        }
        return countryRepo.save(country);
    }

    public List<Country> getFromDb(String name) throws CountryNotFoundException {
        List<CountryEntity> countryList = countryRepo.findByName(name);
        if (countryList.isEmpty()) {
            throw new CountryNotFoundException("Страна с таким названием не была найдена!");
        }
        return countryList.stream()
                .map(Country::toModel)
                .toList();
    }

    public List<Country> getByCountryName(String countryName) throws CountryNotFoundException {
        String apiUrl = "https://restcountries.com/v3.1/name/" + URLEncoder.encode(countryName, StandardCharsets.UTF_8);

        RestTemplate restTemplate = new RestTemplate();

        try {
            String jsonString = restTemplate.getForObject(apiUrl, String.class);

            logger.info("JSON-ответ от {}: {}", apiUrl, jsonString);

            ObjectMapper mapper = new ObjectMapper();

            JsonNode jsonNode = mapper.readTree(jsonString);

            if (jsonNode.isArray() && jsonNode.size() > 0) {
                List<Country> countries = new ArrayList<>();
                for (JsonNode countryNode : jsonNode) {
                    CountryEntity countryEntity = new CountryEntity(countryNode);
                    countryEntity.setCapital(countryNode.get("capital").get(0).asText());
                    countries.add(Country.toModel(countryEntity));
                }
                return countries;
            } else {
                logger.warn("Получен пустой массив данных от запроса: {}", apiUrl);
                throw new CountryNotFoundException("Страна с таким названием не была найдена!");
            }
        } catch (Exception e) {
            logger.error("Ошибка при выполнении запроса", e);
            throw new CountryNotFoundException("Произошла ошибка при выполнении запроса");
        }
    }

    public Long delete(Long id){
        countryRepo.deleteById(id);
        return id;
    }
}