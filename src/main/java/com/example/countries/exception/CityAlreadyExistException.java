package com.example.countries.exception;

public class CityAlreadyExistException extends Exception{
    public CityAlreadyExistException(String message) {
        super(message);
    }
}
