package com.example.countries.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class CacheTest {

  private Cache<String, String> cache;

  @BeforeEach
  public void setUp() {
    cache = new Cache<>();
  }

  @Test
  public void testPutAndGet() {
    cache.put("key1", "value1");
    assertEquals("value1", cache.get("key1"));
  }

  @Test
  public void testPutAndGetList() {
    List<String> valueList = new ArrayList<>();
    valueList.add("value1");
    valueList.add("value2");
    cache.putList("key2", valueList);
    assertEquals(valueList, cache.getList("key2"));
  }

  @Test
  public void testContainsKey() {
    cache.put("key3", "value3");
    assertTrue(cache.containsKey("key3"));
  }

  @Test
  public void testRemove() {
    cache.put("key4", "value4");
    cache.remove("key4");
    assertNull(cache.get("key4"));
  }

  @Test
  public void testClear() {
    cache.put("key5", "value5");
    cache.clear();
    assertNull(cache.get("key5"));
  }
}
