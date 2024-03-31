package com.example.countries.exception;

/**
 * Exception indicating that a country was not found.
 */
public class CountryNotFoundException extends Exception {

  /**
   * Constructs a new CountryNotFoundException with the specified detail message.
   *
   * @param message the detail message (which is saved for later retrieval by the getMessage()
   *               method)
   */
  public CountryNotFoundException(String message) {
    super(message);
  }
}
