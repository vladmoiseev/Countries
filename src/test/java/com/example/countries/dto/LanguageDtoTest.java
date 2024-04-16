package com.example.countries.dto;

import com.example.countries.entity.Language;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LanguageDtoTest {

  @Test
  public void testToModel() {
    Language language = new Language();
    language.setName("TestLanguage");

    LanguageDto languageDto = LanguageDto.toModel(language);

    assertNotNull(languageDto);
    assertEquals("TestLanguage", languageDto.getName());
  }

  @Test
  public void testGetName() {
    LanguageDto languageDto = new LanguageDto();
    languageDto.setName("TestLanguage");

    String name = languageDto.getName();

    assertEquals("TestLanguage", name);
  }

  @Test
  public void testSetName() {
    LanguageDto languageDto = new LanguageDto();

    languageDto.setName("TestLanguage");

    assertEquals("TestLanguage", languageDto.getName());
  }
}
