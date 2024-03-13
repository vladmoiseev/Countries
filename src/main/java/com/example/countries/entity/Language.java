package com.example.countries.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "COUNTRY_LANGUAGE_MAPPING",
            joinColumns = @JoinColumn(name  = "languageId"),
            inverseJoinColumns = @JoinColumn(name = "countryId"))
    private List<Country> countryList = new ArrayList<>();

    public Language() {
        // No initialization logic needed for this constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }
}
