package com.example.countries.repository;

import com.example.countries.entity.Language;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing Language entities.
 */
public interface LanguageRepository extends CrudRepository<Language, Long> {

  /**
   * Finds a language by its name.
   *
   * @param name the name of the language to find
   * @return the language with the specified name, or null if not found
   */
  Language findByName(String name);
}
