package com.example.countries.dto;

import com.example.countries.entity.City;

public class CityDTO {
    private String name;

    public static CityDTO toModel(City entity) {
        CityDTO model = new CityDTO();
        model.setName(entity.getName());
        return model;
    }

    public CityDTO() {
        // No initialization logic needed for this constructor
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
