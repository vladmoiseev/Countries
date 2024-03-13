package com.example.countries.exception;

import com.example.countries.entity.City;

public class CityAlreadyExistException extends Exception{
    public CityAlreadyExistException(String message) {
        super(message);
    }
}
