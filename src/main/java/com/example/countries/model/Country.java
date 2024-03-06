package com.example.countries.model;

import com.example.countries.entity.CountryEntity;

public class Country {
    private String name;
    private String capital;


    public static Country toModel(CountryEntity entity){
        Country model = new Country();
        model.setName(entity.getName());
        model.setCapital(entity.getCapital());
        return model;
    }

    public Country() {
        //сонар попросил комент
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
