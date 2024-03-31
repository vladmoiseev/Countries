package com.example.countries.exception;

/**
 * Exception indicating that a language was not found.
 */
public class LanguageNotFoundException extends Exception {

  /**
   * Constructs a new LanguageNotFoundException with the specified detail message.
   *
   * @param message the detail message (which is saved for later retrieval by the getMessage()
   *               method)
   */
  public LanguageNotFoundException(String message) {
    super(message);
  }
}
