package com.example.countries.controller;

import com.example.countries.dto.LanguageDto;
import com.example.countries.entity.Language;
import com.example.countries.exception.CountryNotFoundException;
import com.example.countries.exception.LanguageAlreadyExistException;
import com.example.countries.exception.LanguageNotFoundException;
import com.example.countries.service.LanguageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LanguageControllerTest {

  @Mock
  private LanguageService languageService;

  @InjectMocks
  private LanguageController languageController;

  private static final Long COUNTRY_ID = 1L;
  private static final Long LANGUAGE_ID = 1L;
  private static final Language TEST_LANGUAGE = new Language();
  private static final String ERROR_MESSAGE = "Произошла ошибка!";

  @BeforeEach
  void setUp() {
    TEST_LANGUAGE.setId(LANGUAGE_ID);
  }

  @Test
  void addLanguage_Success() throws CountryNotFoundException, LanguageAlreadyExistException {
    doNothing().when(languageService).addLanguage(COUNTRY_ID, TEST_LANGUAGE);

    ResponseEntity<?> response = languageController.addLanguage(COUNTRY_ID, TEST_LANGUAGE);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Язык был успешно сохранен!", response.getBody());
    verify(languageService, times(1)).addLanguage(COUNTRY_ID, TEST_LANGUAGE);
  }

  @Test
  void updateLanguage_Success() throws LanguageNotFoundException {
    doNothing().when(languageService).updateLanguage(LANGUAGE_ID, TEST_LANGUAGE);

    ResponseEntity<?> response = languageController.updateLanguage(LANGUAGE_ID, TEST_LANGUAGE);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Язык был успешно изменен!", response.getBody());
    verify(languageService, times(1)).updateLanguage(LANGUAGE_ID, TEST_LANGUAGE);
  }

  @Test
  void deleteLanguage_Success() throws LanguageNotFoundException, CountryNotFoundException {
    doNothing().when(languageService).deleteLanguage(COUNTRY_ID, LANGUAGE_ID);

    ResponseEntity<?> response = languageController.deleteLanguage(COUNTRY_ID, LANGUAGE_ID);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Язык был успешно удален!", response.getBody());
    verify(languageService, times(1)).deleteLanguage(COUNTRY_ID, LANGUAGE_ID);
  }
}
