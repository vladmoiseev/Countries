package com.example.countries.exception;

public class CountryAlreadyExistException extends Exception{
    public CountryAlreadyExistException(String message) {
        super(message);
    }
}
