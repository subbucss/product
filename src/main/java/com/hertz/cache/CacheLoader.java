
package com.hertz.cache;

/**
 * Loader for the objects in the cache. If the loader is available, it is called
 * in case a key is not found in the cache. 
 * 
 * @author Subba
 */
public interface CacheLoader<K, T> {
    public T load(K key);
}
