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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LanguageServiceTest {

  @Mock
  private LanguageRepository languageRepository;

  @Mock
  private CountryRepository countryRepository;

  @Mock
  private Cache<String, CountryDto> countryCache;

  @InjectMocks
  private LanguageService languageService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void addLanguage_Successfully() {
    Country country = new Country();
    Language language = new Language();
    language.setName("English");
    when(countryRepository.findById(anyLong())).thenReturn(Optional.of(country));
    when(languageRepository.findByName(anyString())).thenReturn(null);

    assertDoesNotThrow(() -> languageService.addLanguage(1L, language));

    verify(countryRepository, times(1)).findById(anyLong());
    verify(languageRepository, times(1)).findByName(anyString());
    verify(languageRepository, times(1)).save(any(Language.class));
    verify(countryRepository, times(1)).save(any(Country.class));
    verify(countryCache, times(1)).clear();
  }

  @Test
  void addLanguage_CountryNotFound() {
    when(countryRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(CountryNotFoundException.class, () -> languageService.addLanguage(1L, new Language()));

    verify(countryRepository, times(1)).findById(anyLong());
    verify(languageRepository, never()).findByName(anyString());
    verify(languageRepository, never()).save(any(Language.class));
    verify(countryRepository, never()).save(any(Country.class));
    verify(countryCache, never()).clear();
  }

  @Test
  void getLanguage_Successfully() throws LanguageNotFoundException {
    Language language = new Language();
    language.setName("English");
    when(languageRepository.findById(anyLong())).thenReturn(Optional.of(language));

    LanguageDto languageDto = languageService.getLanguage(1L);

    assertNotNull(languageDto);
    assertEquals("English", languageDto.getName());
  }

  @Test
  void getLanguage_NotFound() {
    when(languageRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(LanguageNotFoundException.class, () -> languageService.getLanguage(1L));
  }

  @Test
  void updateLanguage_Successfully() throws LanguageNotFoundException {
    Language language = new Language();
    language.setName("English");
    when(languageRepository.findById(anyLong())).thenReturn(Optional.of(language));

    assertDoesNotThrow(() -> languageService.updateLanguage(1L, language));

    verify(languageRepository, times(1)).findById(anyLong());
    verify(languageRepository, times(1)).save(any(Language.class));
    verify(countryCache, times(1)).clear();
  }

  @Test
  void updateLanguage_NotFound() {
    when(languageRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(LanguageNotFoundException.class, () -> languageService.updateLanguage(1L, new Language()));

    verify(languageRepository, times(1)).findById(anyLong());
    verify(languageRepository, never()).save(any(Language.class));
    verify(countryCache, never()).clear();
  }

  @Test
  void deleteLanguage_CountryNotFound() {
    when(countryRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(CountryNotFoundException.class, () -> languageService.deleteLanguage(1L, 1L));

    verify(countryRepository, times(1)).findById(anyLong());
    verify(languageRepository, never()).findById(anyLong());
    verify(countryRepository, never()).save(any(Country.class));
    verify(languageRepository, never()).save(any(Language.class));
    verify(countryCache, never()).clear();
  }

  @Test
  void deleteLanguage_LanguageNotFound() {
    Country country = new Country();
    when(countryRepository.findById(anyLong())).thenReturn(Optional.of(country));
    when(languageRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(LanguageNotFoundException.class, () -> languageService.deleteLanguage(1L, 1L));

    verify(countryRepository, times(1)).findById(anyLong());
    verify(languageRepository, times(1)).findById(anyLong());
    verify(countryRepository, never()).save(any(Country.class));
    verify(languageRepository, never()).save(any(Language.class));
    verify(countryCache, never()).clear();
  }
}
