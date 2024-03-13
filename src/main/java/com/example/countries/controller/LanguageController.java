package com.example.countries.controller;

import com.example.countries.entity.Language;
import com.example.countries.exception.CountryNotFoundException;
import com.example.countries.exception.LanguageAlreadyExistException;
import com.example.countries.exception.LanguageNotFoundException;
import com.example.countries.service.LanguageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/languages")
public class LanguageController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    private static final String ERROR_MESSAGE = "Произошла ошибка!";

    @PostMapping
    public ResponseEntity<?> addLanguage(@RequestParam Long countryId, @RequestBody Language language) {
        try {
            languageService.addLanguage(countryId, language);
            return ResponseEntity.ok("Язык был успешно сохранен!");
        } catch (CountryNotFoundException | LanguageAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }

    @GetMapping
    public ResponseEntity<?> getLanguage(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(languageService.getLanguage(id));
        } catch (LanguageNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateLanguage(@RequestParam Long id, @RequestBody Language updatedLanguage) {
        try {
            languageService.updateLanguage(id, updatedLanguage);
            return ResponseEntity.ok("Язык был успешно изменен!");
        } catch (LanguageNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteLanguage(@RequestParam Long countryId, @RequestParam Long languageId) {
        try {
            languageService.deleteLanguage(countryId, languageId);
            return ResponseEntity.ok("Язык был успешно удален!");
        } catch (LanguageNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }
}
