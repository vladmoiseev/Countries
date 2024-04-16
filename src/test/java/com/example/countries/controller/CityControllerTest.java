package com.example.countries.controller;

import com.example.countries.entity.City;
import com.example.countries.exception.CityAlreadyExistException;
import com.example.countries.exception.CityNotFoundException;
import com.example.countries.exception.CountryNotFoundException;
import com.example.countries.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CityControllerTest {

  @Mock
  private CityService cityService;

  @InjectMocks
  private CityController cityController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testAddCity_Success() throws CountryNotFoundException, CityAlreadyExistException {
    City city = new City();
    doNothing().when(cityService).addCity(anyLong(), any());

    ResponseEntity<?> response = cityController.addCity(1L, city);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Город был успешно сохранен!", response.getBody());
    verify(cityService, times(1)).addCity(anyLong(), any());
  }

  @Test
  void testAddCity_CountryNotFoundException() throws CountryNotFoundException, CityAlreadyExistException {
    City city = new City();
    doThrow(new CountryNotFoundException("Country not found")).when(cityService).addCity(anyLong(), any());

    assertThrows(HttpClientErrorException.class, () -> {
      cityController.addCity(1L, city);
    });
  }

  @Test
  void testGetCity_Success() throws CityNotFoundException {
    City city = new City();
    when(cityService.getCity(anyLong())).thenAnswer(invocation -> {
      Long id = invocation.getArgument(0);
      if (id.equals(1L)) {
        return city;
      } else {
        throw new CityNotFoundException("City not found");
      }
    });

    ResponseEntity<?> response = cityController.getCity(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(city, response.getBody());
    verify(cityService, times(1)).getCity(anyLong());
  }


  @Test
  void testGetCity_CityNotFoundException() throws CityNotFoundException {
    when(cityService.getCity(anyLong())).thenThrow(new CityNotFoundException("City not found"));

    assertThrows(HttpClientErrorException.class, () -> {
      cityController.getCity(1L);
    });
  }

  @Test
  void testUpdateCity_Success() throws CityNotFoundException {
    City city = new City();
    doNothing().when(cityService).updateCity(anyLong(), any());

    ResponseEntity<?> response = cityController.updateCity(1L, city);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Город был успешно изсенен!", response.getBody());
    verify(cityService, times(1)).updateCity(anyLong(), any());
  }

  @Test
  void testUpdateCity_CityNotFoundException() throws CityNotFoundException {
    City city = new City();
    doThrow(new CityNotFoundException("City not found")).when(cityService).updateCity(anyLong(), any());

    assertThrows(HttpClientErrorException.class, () -> {
      cityController.updateCity(1L, city);
    });
  }

  @Test
  void testDeleteCity_Success() throws CityNotFoundException {
    doNothing().when(cityService).deleteCity(anyLong());

    ResponseEntity<?> response = cityController.deleteCity(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Город был успешно удален!", response.getBody());
    verify(cityService, times(1)).deleteCity(anyLong());
  }

  @Test
  void testDeleteCity_CityNotFoundException() throws CityNotFoundException {
    doThrow(new CityNotFoundException("City not found")).when(cityService).deleteCity(anyLong());

    assertThrows(HttpClientErrorException.class, () -> {
      cityController.deleteCity(1L);
    });
  }
}
