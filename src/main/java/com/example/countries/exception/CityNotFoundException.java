package com.example.countries.exception;

/**
 * Exception indicating that a city was not found.
 */
public class CityNotFoundException extends Exception {

  /**
   * Constructs a new CityNotFoundException with the specified detail message.
   *
   * @param message the detail message (which is saved for later retrieval by the getMessage()
   *               method)
   */
  public CityNotFoundException(String message) {
    super(message);
  }
}
