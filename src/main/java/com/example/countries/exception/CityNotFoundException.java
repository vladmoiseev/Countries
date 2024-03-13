package com.example.countries.exception;

import com.example.countries.entity.City;

public class CityNotFoundException extends Exception{
    public CityNotFoundException(String message) {
        super(message);
    }
}
