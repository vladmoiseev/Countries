package com.example.countries.controller;

import com.example.countries.entity.CountryEntity;
import com.example.countries.exception.CountryAlreadyExistException;
import com.example.countries.exception.CountryNotFoundException;
import com.example.countries.model.Country;
import com.example.countries.service.CountryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private static final String ERROR_MESSAGE = "Произошла ошибка!";

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping
    public ResponseEntity addCountry(@RequestBody CountryEntity country) {
        try {
            countryService.add(country);
            return ResponseEntity.ok("Страна успешно сохранена!");
        } catch (CountryAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }

    @GetMapping
    public ResponseEntity getCountryFromDb(@RequestParam(required = false) String name) {
        try {
            return ResponseEntity.ok(countryService.getFromDb(name));
        } catch (CountryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }

    @GetMapping("/name")
    public ResponseEntity<?> getCountryByName(@RequestParam String name) {
        try {
            List<Country> countries = countryService.getByCountryName(name);
            return ResponseEntity.ok(countries);
        } catch (CountryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCountry(@PathVariable Long id){
        try {
            countryService.delete(id);
            return ResponseEntity.ok("Страна успешно удалена!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }
}