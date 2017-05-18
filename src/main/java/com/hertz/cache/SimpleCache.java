
package com.hertz.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * Simple cache for simple cases
 * 
 * @author Subba
 */
@Component()
public class SimpleCache<K,T> implements Cache<K,T> {
    
    private Map<K,T> cache;
    
    public SimpleCache() {
    	cache = new HashMap<K,T>();
    }
    
    public void put(K key, T obj) {
        cache.put(key, obj);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public T get(K key, CacheLoader<K, T> loader) {
        try {
            T obj = cache.get(key);
            if (obj == null) {
            	obj = loader.load(key);
            }
            return obj;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

	public T getIfPresent(K key){
         T obj = cache.get(key);
         return obj;
    }
    
    public void remove(K key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }

	@Override
	public String toString() {
		return "SimpleCache [" + (cache != null ? "cache=" + cache : "") + "]";
	}
    
    
 }
