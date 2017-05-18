
package com.hertz.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;

/**
 * An implementation of the LRU cache, using the google guava library
 * 
 * @author Subba
 */
@Component()
public class LRUCache<K,T> implements Cache<K,T> {
    
    private com.google.common.cache.Cache<K,T> guavaCache;
    
    public LRUCache() {
        guavaCache = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .build();
    }
    
    public LRUCache(int size) {
        guavaCache = CacheBuilder.newBuilder()
            .maximumSize(size)
            .build();
    }

    public void put(K key, T obj) {
        guavaCache.put(key, obj);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public T get(K key, CacheLoader<K, T> loader) {
        try {
            LoaderCaller lc = new LoaderCaller(loader, key);
            T obj = guavaCache.get(key, lc);
            return obj;
        } catch (ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }

	public T getIfPresent(K key){
         T obj = guavaCache.getIfPresent(key);
         return obj;
    }
    
    public void remove(K key) {
        guavaCache.invalidate(key);
    }

    public void clear() {
        guavaCache.cleanUp();
    }
    
    @SuppressWarnings("rawtypes")
	public static class LoaderCaller<K, T> implements Callable {

        private CacheLoader<K,T> loader = null;
        private K key = null;
        
        public LoaderCaller(CacheLoader<K, T> loader, K key) {
            this.loader = loader;
            this.key = key;
        }
        
        public Object call() throws Exception {
            return this.loader.load(key);
        }   
    }
}
