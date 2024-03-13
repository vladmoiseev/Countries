package com.example.countries.service;

import com.example.countries.dto.LanguageDTO;
import com.example.countries.entity.Country;
import com.example.countries.entity.Language;
import com.example.countries.exception.CountryNotFoundException;
import com.example.countries.exception.LanguageAlreadyExistException;
import com.example.countries.exception.LanguageNotFoundException;
import com.example.countries.repository.CountryRepository;
import com.example.countries.repository.LanguageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;
    private final CountryRepository countryRepository;
    private static final String LANGUAGE_NOT_FOUND_STRING = "Язык не найден!";

    @Autowired
    public LanguageService(LanguageRepository languageRepository, CountryRepository countryRepository) {
        this.languageRepository = languageRepository;
        this.countryRepository = countryRepository;
    }

    @Transactional
    public void addLanguage(Long countryId, Language language) throws CountryNotFoundException, LanguageAlreadyExistException {
        Country countryEntity = countryRepository.findById(countryId).orElse(null);
        if (countryEntity == null)
            throw new CountryNotFoundException("Не удалось добавить язык. Язык не найден!");

        if (countryEntity.getLanguageList().stream().anyMatch(existingLanguage -> existingLanguage.getName().equals(language.getName()))) {
            throw new LanguageAlreadyExistException("Язык уже существует в данной стране!");
        }

        Language existingLanguage = languageRepository.findByName(language.getName());

        if (existingLanguage != null) {
            countryEntity.getLanguageList().add(existingLanguage);
        } else {
            languageRepository.save(language);
            countryEntity.getLanguageList().add(language);
        }

        countryRepository.save(countryEntity);
    }

    public LanguageDTO getLanguage(Long id) throws LanguageNotFoundException {
        Language language = languageRepository.findById(id).orElse(null);
        if (language != null) {
            return LanguageDTO.toModel(language);
        } else {
            throw new LanguageNotFoundException(LANGUAGE_NOT_FOUND_STRING);
        }
    }

    public void updateLanguage(Long id, Language language) throws LanguageNotFoundException {
        Language languageEntity = languageRepository.findById(id).orElse(null);
        if (languageEntity != null) {
            languageEntity.setName(language.getName());
            languageRepository.save(languageEntity);
        } else {
            throw new LanguageNotFoundException(LANGUAGE_NOT_FOUND_STRING);
        }
    }

    @Transactional
    public void deleteLanguage(Long countryId, Long languageId) throws LanguageNotFoundException, CountryNotFoundException {
        Country countryEntity = countryRepository.findById(countryId).orElse(null);
        if (countryEntity == null)
            throw new CountryNotFoundException("Блюдо не найдено");

        Language languageEntity = languageRepository.findById(languageId).orElse(null);
        if (languageEntity == null)
            throw new LanguageNotFoundException(LANGUAGE_NOT_FOUND_STRING);

        countryEntity.getLanguageList().remove(languageEntity);
        countryRepository.save(countryEntity);
        languageEntity.getCountryList().remove(countryEntity);
        languageRepository.save(languageEntity);
    }
}
