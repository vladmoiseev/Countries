package com.example.countries.repository;

import com.example.countries.entity.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Long> {
    Country findByName(String name);
}

