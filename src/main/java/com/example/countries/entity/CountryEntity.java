package com.example.countries.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import com.fasterxml.jackson.databind.JsonNode;

@Entity
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String capital;

    public CountryEntity(){
    }

    public CountryEntity(JsonNode jsonNode) {
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
}

