package com.example.countries.dto;

import com.example.countries.entity.Language;

/**
 * Data Transfer Object (DTO) class representing language information.
 */
public class LanguageDto {
  private String name;

  /**
   * Converts a Language entity to a LanguageDto model.
   *
   * @param entity the Language entity to convert
   * @return the converted LanguageDto model
   */
  public static LanguageDto toModel(Language entity) {
    LanguageDto model = new LanguageDto();
    model.setName(entity.getName());
    return model;
  }

  /**
   * Constructs a new LanguageDto instance.
   * No initialization logic needed for this constructor.
   */
  public LanguageDto() {
    // No initialization logic needed for this constructor
  }

  /**
   * Gets the name of the language.
   *
   * @return the name of the language
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the language.
   *
   * @param name the name of the language
   */
  public void setName(String name) {
    this.name = name;
  }
}
