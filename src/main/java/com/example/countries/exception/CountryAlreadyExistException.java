package com.example.countries.exception;

/**
 * Exception indicating that a country already exists.
 */
public class CountryAlreadyExistException extends Exception {

  /**
   * Constructs a new CountryAlreadyExistException with the specified detail message.
   *
   * @param message the detail message (which is saved for later retrieval by the getMessage()
   *               method)
   */
  public CountryAlreadyExistException(String message) {
    super(message);
  }
}
