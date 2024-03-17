package com.example.countries.component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class Cache<K, V> {
    private static final int MAX_ENTRIES = 10;

    private final Map<K, Object> countryCache = new LinkedHashMap<>(MAX_ENTRIES, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > MAX_ENTRIES;
        }
    };

    public void put(K key, V value) {
        countryCache.put(key, value);
    }

    public void putList(K key, List<V> valueList) {
        countryCache.put(key, valueList);
    }

    public V get(K key) {
        return (V) countryCache.get(key);
    }

    public List<V> getList(K key) {
        return (List<V>) countryCache.get(key);
    }

    public boolean containsKey(K key) {
        return countryCache.containsKey(key);
    }

    public void remove(K key) {
        countryCache.remove(key);
    }

    public void clear() {
        countryCache.clear();
    }
}