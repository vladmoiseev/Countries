package com.example.countries.dto;

import com.example.countries.entity.Country;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CountryDtoTest {

  @Test
  void testToModel() {
    Country country = new Country();
    country.setName("TestCountry");
    country.setCapital("TestCapital");

    CountryDto countryDto = CountryDto.toModel(country);

    assertNotNull(countryDto);
    assertEquals("TestCountry", countryDto.getName());
    assertEquals("TestCapital", countryDto.getCapital());
  }

  @Test
  void testGetName() {
    CountryDto countryDto = new CountryDto();
    countryDto.setName("TestCountry");

    String name = countryDto.getName();

    assertEquals("TestCountry", name);
  }

  @Test
  void testSetName() {
    CountryDto countryDto = new CountryDto();

    countryDto.setName("TestCountry");

    assertEquals("TestCountry", countryDto.getName());
  }

  @Test
  void testGetCapital() {
    CountryDto countryDto = new CountryDto();
    countryDto.setCapital("TestCapital");

    String capital = countryDto.getCapital();

    assertEquals("TestCapital", capital);
  }

  @Test
  void testSetCapital() {
    CountryDto countryDto = new CountryDto();

    countryDto.setCapital("TestCapital");

    assertEquals("TestCapital", countryDto.getCapital());
  }
}
