package com.example.countries.repository;

import com.example.countries.entity.Language;
import org.springframework.data.repository.CrudRepository;

public interface LanguageRepository extends CrudRepository<Language, Long> {
    Language findByName(String name);
}
