package com.example.countries.controller;

import com.example.countries.entity.Country;
import com.example.countries.exception.CountryAlreadyExistException;
import com.example.countries.exception.CountryNotFoundException;
import com.example.countries.dto.CountryDTO;
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
    public ResponseEntity<?> addCountry(@RequestBody Country country) {
        try {
            countryService.addCountry(country);
            return ResponseEntity.ok("Страна была успешно сохранена!");
        } catch (CountryAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }

    @GetMapping
    public ResponseEntity<?> getCountry(@RequestParam(required = false) String name) {
        try {
            return ResponseEntity.ok(countryService.getCountry(name));
        } catch (CountryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }

    @GetMapping("/name")
    public ResponseEntity<?> getCountryByName(@RequestParam String name) {
        try {
            List<CountryDTO> countries = countryService.getByCountryName(name);
            return ResponseEntity.ok(countries);
        } catch (CountryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateCountry(@RequestParam String name, @RequestBody Country updatedCountry) {
        try {
            countryService.updateCountry(name, updatedCountry);
            return ResponseEntity.ok("Страна была успешно изменена");
        } catch (CountryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCountry(@RequestParam Long id) {
        try {
            countryService.deleteCountry(id);
            return ResponseEntity.ok("Страна была успешно удалена");
        } catch (CountryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }
}