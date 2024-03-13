package com.example.countries.controller;

import com.example.countries.entity.City;
import com.example.countries.exception.CityAlreadyExistException;
import com.example.countries.exception.CityNotFoundException;
import com.example.countries.exception.CountryNotFoundException;
import com.example.countries.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    private static final String ERROR_MESSAGE = "Произошла ошибка!";

    @PostMapping
    public ResponseEntity<?> addCity(@RequestParam Long countryId, @RequestBody City city) {
        try {
            cityService.addCity(countryId, city);
            return ResponseEntity.ok("Город был успешно сохранен!");
        } catch (CountryNotFoundException | CityAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }


    @GetMapping
    public ResponseEntity<?> getCity(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(cityService.getCity(id));
        } catch (CityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateCity(@RequestParam Long id, @RequestBody City updatedCity) {
        try {
            cityService.updateCity(id, updatedCity);
            return ResponseEntity.ok("Город был успешно изсенен!");
        } catch (CityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCity(@RequestParam Long id) {
        try {
            cityService.deleteCity(id);
            return ResponseEntity.ok("Город был успешно удален!");
        } catch (CityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }
}
