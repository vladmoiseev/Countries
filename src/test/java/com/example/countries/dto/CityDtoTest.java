package com.example.countries.dto;

import com.example.countries.entity.City;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CityDtoTest {

  @Test
  public void testToModel() {
    City city = new City();
    city.setName("TestCity");

    CityDto cityDto = CityDto.toModel(city);

    assertNotNull(cityDto);
    assertEquals("TestCity", cityDto.getName());
  }

  @Test
  public void testGetName() {
    CityDto cityDto = new CityDto();
    cityDto.setName("TestCity");

    String name = cityDto.getName();

    assertEquals("TestCity", name);
  }

  @Test
  public void testSetName() {
    CityDto cityDto = new CityDto();

    cityDto.setName("TestCity");

    assertEquals("TestCity", cityDto.getName());
  }
}
