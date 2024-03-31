package com.example.countries.exception;

/**
 * Exception indicating that a language already exists.
 */
public class LanguageAlreadyExistException extends Exception {

  /**
   * Constructs a new LanguageAlreadyExistException with the specified detail message.
   *
   * @param message the detail message (which is saved for later retrieval by the getMessage()
   *               method)
   */
  public LanguageAlreadyExistException(String message) {
    super(message);
  }
}
