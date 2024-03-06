package com.example.countries.repository;

import com.example.countries.entity.CountryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
public interface CountryRepo extends CrudRepository<CountryEntity, Long> {
    List<CountryEntity> findByName(String name);
}

