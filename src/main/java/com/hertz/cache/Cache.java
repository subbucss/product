
package com.hertz.cache;

/**
 * Basic Cache contract. Its implementations for different service providers need
 * to be added. 
 * 
 * @author Subba
 */
public interface Cache<K, T> {
    public void put(K key, T obj);
    public T get(K key, CacheLoader<K, T> loader);
    public T getIfPresent(K key);
    public void remove(K key);
    public void clear();
}
