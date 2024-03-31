package com.example.countries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения для запуска Spring Boot приложения.
 */
@SpringBootApplication
public class CountriesApplication {

  /**
   * Главный метод, который запускает Spring Boot приложение.
   *
   * @param args Аргументы командной строки.
   */
  public static void main(String[] args) {
    SpringApplication.run(CountriesApplication.class, args);
  }

}
