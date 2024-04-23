package com.example.countries.dto;

import com.example.countries.entity.Language;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LanguageDtoTest {

  @Test
  void testToModel() {
    Language language = new Language();
    language.setName("TestLanguage");

    LanguageDto languageDto = LanguageDto.toModel(language);

    assertNotNull(languageDto);
    assertEquals("TestLanguage", languageDto.getName());
  }

  @Test
  void testGetName() {
    LanguageDto languageDto = new LanguageDto();
    languageDto.setName("TestLanguage");

    String name = languageDto.getName();

    assertEquals("TestLanguage", name);
  }

  @Test
  void testSetName() {
    LanguageDto languageDto = new LanguageDto();

    languageDto.setName("TestLanguage");

    assertEquals("TestLanguage", languageDto.getName());
  }
}
