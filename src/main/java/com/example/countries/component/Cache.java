package com.example.countries.component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * A simple cache implementation using a LinkedHashMap.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
@Component
public class Cache<K, V> {
  private static final int MAX_ENTRIES = 10;

  private final Map<K, Object> countryCache = new LinkedHashMap<>(MAX_ENTRIES, 0.75f, true) {
    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
      return size() > MAX_ENTRIES;
    }
  };

  /**
   * Put a key-value pair into the cache.
   *
   * @param key   the key with which the specified value is to be associated
   * @param value the value to be associated with the specified key
   */
  public void put(K key, V value) {
    countryCache.put(key, value);
  }

  /**
   * Put a key and a list of values into the cache.
   *
   * @param key       the key with which the specified list is to be associated
   * @param valueList the list of values to be associated with the specified key
   */
  public void putList(K key, List<V> valueList) {
    countryCache.put(key, valueList);
  }

  /**
   * Retrieve the value associated with the specified key from the cache.
   *
   * @param key the key whose associated value is to be returned
   * @return the value to which the specified key is mapped, or null if the cache contains no
   *     mapping for the key
   */
  public V get(K key) {
    return (V) countryCache.get(key);
  }

  /**
   * Retrieve the list of values associated with the specified key from the cache.
   *
   * @param key the key whose associated list of values is to be returned
   * @return the list of values to which the specified key is mapped, or null if the cache contains
   *     no mapping for the key
   */
  public List<V> getList(K key) {
    return (List<V>) countryCache.get(key);
  }

  /**
   * Check if the cache contains the specified key.
   *
   * @param key the key whose presence in the cache is to be tested
   * @return true if the cache contains a mapping for the specified key, otherwise false
   */
  public boolean containsKey(K key) {
    return countryCache.containsKey(key);
  }

  /**
   * Remove the mapping for the specified key from the cache if present.
   *
   * @param key the key whose mapping is to be removed from the cache
   */
  public void remove(K key) {
    countryCache.remove(key);
  }

  /**
   * Clear all entries from the cache.
   */
  public void clear() {
    countryCache.clear();
  }
}
