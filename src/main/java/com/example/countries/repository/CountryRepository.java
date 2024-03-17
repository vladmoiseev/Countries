package com.example.countries.repository;

import com.example.countries.entity.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CountryRepository extends CrudRepository<Country, Long> {
    Country findByName(String name);

    @Query("SELECT d FROM Country d JOIN d.languageList i WHERE i.id = :languageId")
    List<Country> findCountriesByLanguageList_Id(Long languageId);
}

