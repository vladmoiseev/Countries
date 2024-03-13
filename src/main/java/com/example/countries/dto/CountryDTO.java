package com.example.countries.dto;

import com.example.countries.entity.Country;

public class CountryDTO {
    private String name;
    private String capital;


    public static CountryDTO toModel(Country entity){
        CountryDTO model = new CountryDTO();
        model.setName(entity.getName());
        model.setCapital(entity.getCapital());
        return model;
    }

    public CountryDTO() {
        // No initialization logic needed for this constructor
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
