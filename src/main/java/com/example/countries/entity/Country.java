package com.example.countries.entity;

import jakarta.persistence.*;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String capital;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "country")
    private List<City> cityList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "COUNTRY_LANGUAGE_MAPPING",
            joinColumns = @JoinColumn(name = "countryId"),
            inverseJoinColumns = @JoinColumn(name = "languageId"))
    private List<Language> languageList = new ArrayList<>();

    public Country(){
    }

    public Country(JsonNode jsonNode) {
        this.name = jsonNode.get("name").get("common").asText();
        JsonNode capitalNode = jsonNode.get("capital");
        this.capital = (capitalNode != null && !capitalNode.isNull()) ? capitalNode.asText() : null;
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

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public List<Language> getLanguageList() {
        return languageList;
    }

    public void setLanguageList(List<Language> languageList) {
        this.languageList = languageList;
    }
}

