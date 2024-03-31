package com.example.countries.exception;

/**
 * Exception indicating that a city already exists.
 */
public class CityAlreadyExistException extends Exception {

  /**
   * Constructs a new CityAlreadyExistException with the specified detail message.
   *
   * @param message the detail message (which is saved for later retrieval by the getMessage()
   *               method)
   */
  public CityAlreadyExistException(String message) {
    super(message);
  }
}
