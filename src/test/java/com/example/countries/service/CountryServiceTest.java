package com.example.countries.service;

import com.example.countries.component.Cache;
import com.example.countries.dto.CountryDto;
import com.example.countries.entity.Country;
import com.example.countries.exception.CountryAlreadyExistException;
import com.example.countries.exception.CountryNotFoundException;
import com.example.countries.repository.CountryRepository;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


class CountryServiceTest {

  @Mock
  private CountryRepository countryRepository;

  @Mock
  private Cache<String, CountryDto> countryCache;

  @InjectMocks
  private CountryService countryService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void addCountry_Success() {
    Country country = new Country();
    country.setName("Test Country");

    when(countryRepository.findByName("Test Country")).thenReturn(null);

    assertDoesNotThrow(() -> countryService.addCountry(country));
    verify(countryRepository, times(1)).save(country);
    verify(countryCache, times(1)).clear();
  }

  @Test
  void addCountry_AlreadyExists() {
    Country country = new Country();
    country.setName("Existing Country");

    when(countryRepository.findByName("Existing Country")).thenReturn(country);

    assertThrows(CountryAlreadyExistException.class, () -> countryService.addCountry(country));
    verify(countryRepository, never()).save(any());
    verify(countryCache, never()).clear();
  }

  @Test
  void addCountriesBulk_Successful() {
    List<Country> countriesToAdd = new ArrayList<>();
    countriesToAdd.add(new Country("NewCountry1"));
    countriesToAdd.add(new Country("NewCountry2"));

    when(countryRepository.saveAll(any())).thenReturn(countriesToAdd);

    assertDoesNotThrow(() -> countryService.addCountriesBulk(countriesToAdd));
  }

  @Test
  void addCountriesBulk_AllCountriesExist() {
    List<Country> countriesToAdd = new ArrayList<>();
    countriesToAdd.add(new Country("Country1"));
    countriesToAdd.add(new Country("Country2"));

    CountryAlreadyExistException exception = assertThrows(CountryAlreadyExistException.class, () -> countryService.addCountriesBulk(countriesToAdd));
    assertEquals("Все страны из списка уже есть в базе.", exception.getMessage());
  }

  @Test
  void addCountriesBulk_PartiallySuccessful() {
    List<Country> countriesToAdd = new ArrayList<>();
    countriesToAdd.add(new Country("Country1"));
    countriesToAdd.add(new Country("NewCountry1"));
    countriesToAdd.add(new Country("NewCountry2"));

    List<Country> savedCountries = new ArrayList<>();
    savedCountries.add(new Country("Country1"));
    when(countryRepository.saveAll(any())).thenReturn(savedCountries);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> countryService.addCountriesBulk(countriesToAdd));
    assertEquals("Ошибка при добавлении новых стран.", exception.getMessage());
  }

  @Test
  void getCountry_ExistsInCache() throws CountryNotFoundException {
    String countryName = "Test Country";
    CountryDto countryDto = new CountryDto();
    countryDto.setName(countryName);

    when(countryCache.containsKey(countryName)).thenReturn(true);
    when(countryCache.get(countryName)).thenReturn(countryDto);

    assertEquals(countryDto, countryService.getCountry(countryName));
    verify(countryRepository, never()).findByName(any());
  }

  @Test
  void getCountry_NotExistsInCache() throws CountryNotFoundException {
    String countryName = "Test Country";
    Country country = new Country();
    country.setName(countryName);
    CountryDto countryDto = new CountryDto();
    countryDto.setName(countryName);

    when(countryCache.containsKey(countryName)).thenReturn(false);
    when(countryRepository.findByName(countryName)).thenReturn(country);

    CountryDto retrievedCountryDto = countryService.getCountry(countryName);

    assertEquals(countryDto.getName(), retrievedCountryDto.getName());
    assertEquals(countryDto.getCapital(), retrievedCountryDto.getCapital());

    verify(countryCache).put(eq(countryName), any(CountryDto.class));
  }

  @Test
  void getCountriesWithLanguage_ExistsInCache() throws CountryNotFoundException {
    Long languageId = 1L;
    String cacheKey = "language_" + languageId;
    List<CountryDto> countryDtos = new ArrayList<>();

    when(countryCache.containsKey(cacheKey)).thenReturn(true);
    when(countryCache.getList(cacheKey)).thenReturn(countryDtos);

    assertEquals(countryDtos, countryService.getCountriesWithLanguage(languageId));
    verify(countryRepository, never()).findCountriesByLanguageList_Id(any());
  }

  @Test
  void getCountriesWithLanguage_NotExistsInCache() {
    Long languageId = 1L;
    String cacheKey = "language_" + languageId;

    when(countryCache.containsKey(cacheKey)).thenReturn(false);

    when(countryRepository.findCountriesByLanguageList_Id(anyLong())).thenReturn(Collections.emptyList());

    assertThrows(CountryNotFoundException.class, () -> {
      countryService.getCountriesWithLanguage(languageId);
    });

    verify(countryCache, never()).putList(anyString(), anyList());
  }

  @Test
  void updateCountry_Success() throws CountryNotFoundException {
    String countryName = "Test Country";
    Country country = new Country();
    country.setName(countryName);

    when(countryRepository.findByName(countryName)).thenReturn(country);

    assertDoesNotThrow(() -> countryService.updateCountry(countryName, country));
    verify(countryRepository, times(1)).save(country);
    verify(countryCache, times(1)).clear();
  }

  @Test
  void updateCountry_NotFound() {
    String countryName = "Non-existent Country";
    Country country = new Country();
    country.setName(countryName);

    when(countryRepository.findByName(countryName)).thenReturn(null);

    assertThrows(CountryNotFoundException.class, () -> countryService.updateCountry(countryName, country));
    verify(countryRepository, never()).save(any());
    verify(countryCache, never()).clear();
  }

  @Test
  void deleteCountry_Success() throws CountryNotFoundException {
    Long countryId = 1L;
    Country country = new Country();
    country.setId(countryId);

    when(countryRepository.findById(countryId)).thenReturn(java.util.Optional.of(country));

    assertDoesNotThrow(() -> countryService.deleteCountry(countryId));
    verify(countryRepository, times(1)).deleteById(countryId);
    verify(countryCache, times(1)).clear();
  }

  @Test
  void deleteCountry_NotFound() {
    Long countryId = 1L;

    when(countryRepository.findById(countryId)).thenReturn(java.util.Optional.empty());

    assertThrows(CountryNotFoundException.class, () -> countryService.deleteCountry(countryId));
    verify(countryRepository, never()).deleteById(any());
    verify(countryCache, never()).clear();
  }
}
