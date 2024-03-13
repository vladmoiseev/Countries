package com.example.countries.dto;

import com.example.countries.entity.Language;

public class LanguageDTO {
    private String name;
    public static LanguageDTO toModel(Language entity){
        LanguageDTO model = new LanguageDTO();
        model.setName(entity.getName());
        return model;
    }

    public LanguageDTO() {
        // No initialization logic needed for this constructor
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
