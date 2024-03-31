package com.example.countries.service;

import com.example.countries.component.Cache;
import com.example.countries.dto.CountryDto;
import com.example.countries.dto.LanguageDto;
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

/**
 * Сервисный класс для выполнения операций с объектами Language.
 */
@Service
public class LanguageService {

  private final LanguageRepository languageRepository;
  private final CountryRepository countryRepository;
  private final Cache<String, CountryDto> countryCache;
  private static final String LANGUAGE_NOT_FOUND_STRING = "Язык не найден!";

  /**
   * Конструктор сервисного класса LanguageService.
   *
   * @param languageRepository Репозиторий для объектов Language.
   * @param countryRepository  Репозиторий для объектов Country.
   * @param countryCache       Кэш для хранения информации о странах.
   */
  @Autowired
  public LanguageService(LanguageRepository languageRepository, CountryRepository countryRepository,
                         Cache<String, CountryDto> countryCache) {
    this.languageRepository = languageRepository;
    this.countryRepository = countryRepository;
    this.countryCache = countryCache;
  }

  /**
   * Добавляет язык к стране.
   *
   * @param countryId Идентификатор страны.
   * @param language  Объект Language, который необходимо добавить.
   * @throws CountryNotFoundException      Если страна не найдена.
   * @throws LanguageAlreadyExistException Если язык уже существует в данной стране.
   */
  @Transactional
  public void addLanguage(Long countryId, Language language)
      throws CountryNotFoundException, LanguageAlreadyExistException {
    Country countryEntity = countryRepository.findById(countryId).orElse(null);
    if (countryEntity == null) {
      throw new CountryNotFoundException("Не удpалось добавить язык. Язык не найден!");
    }

    if (countryEntity.getLanguageList().stream()
        .anyMatch(existingLanguage -> existingLanguage.getName().equals(language.getName()))) {
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

    countryCache.clear();
  }

  /**
   * Возвращает объект LanguageDto по его идентификатору.
   *
   * @param id Идентификатор языка.
   * @return Объект LanguageDto.
   * @throws LanguageNotFoundException Если язык не найден.
   */
  public LanguageDto getLanguage(Long id) throws LanguageNotFoundException {
    Language language = languageRepository.findById(id).orElse(null);
    if (language != null) {
      return LanguageDto.toModel(language);
    } else {
      throw new LanguageNotFoundException(LANGUAGE_NOT_FOUND_STRING);
    }
  }

  /**
   * Обновляет информацию о языке.
   *
   * @param id       Идентификатор языка.
   * @param language Объект Language с обновленными данными.
   * @throws LanguageNotFoundException Если язык не найден.
   */
  public void updateLanguage(Long id, Language language) throws LanguageNotFoundException {
    Language languageEntity = languageRepository.findById(id).orElse(null);
    if (languageEntity != null) {
      languageEntity.setName(language.getName());
      languageRepository.save(languageEntity);

      countryCache.clear();
    } else {
      throw new LanguageNotFoundException(LANGUAGE_NOT_FOUND_STRING);
    }
  }

  /**
   * Удаляет язык из страны.
   *
   * @param countryId  Идентификатор страны.
   * @param languageId Идентификатор языка.
   * @throws LanguageNotFoundException Если язык не найден.
   * @throws CountryNotFoundException  Если страна не найдена.
   */
  @Transactional
  public void deleteLanguage(Long countryId, Long languageId)
      throws LanguageNotFoundException, CountryNotFoundException {
    Country countryEntity = countryRepository.findById(countryId).orElse(null);
    if (countryEntity == null) {
      throw new CountryNotFoundException("Блюдо не найдено");
    }

    Language languageEntity = languageRepository.findById(languageId).orElse(null);
    if (languageEntity == null) {
      throw new LanguageNotFoundException(LANGUAGE_NOT_FOUND_STRING);
    }

    countryEntity.getLanguageList().remove(languageEntity);
    countryRepository.save(countryEntity);
    languageEntity.getCountryList().remove(countryEntity);
    languageRepository.save(languageEntity);

    countryCache.clear();
  }
}
