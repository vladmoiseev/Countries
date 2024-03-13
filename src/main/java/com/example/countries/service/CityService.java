package com.example.countries.service;

import com.example.countries.dto.CityDTO;
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

import javax.swing.*;

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

    public void addCity(Long id, City city) throws CountryNotFoundException, CityAlreadyExistException {
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

    public CityDTO getCity(Long id) throws CityNotFoundException {
        City cityEntity = cityRepository.findById(id).orElse(null);
        if (cityEntity != null) {
            return CityDTO.toModel(cityEntity);
        } else {
            throw new CityNotFoundException(CITY_NOT_FOUND_STRING);
        }
    }

    public void updateCity(Long id, City city) throws CityNotFoundException {
        City cityEntity = cityRepository.findById(id).orElse(null);
        if (cityEntity != null) {
            cityEntity.setName(city.getName());
            cityRepository.save(cityEntity);
        } else {
            throw new CityNotFoundException(CITY_NOT_FOUND_STRING);
        }
    }

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
