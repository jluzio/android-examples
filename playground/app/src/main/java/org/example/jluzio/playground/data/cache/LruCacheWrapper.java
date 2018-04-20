package org.example.jluzio.playground.data.cache;

import android.util.LruCache;

public class LruCacheWrapper<K, V> implements Cache<K, V> {
    private LruCache<K, V> cache;

    public LruCacheWrapper(LruCache<K, V> cache) {
        this.cache = cache;
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public V put(K key, V value) {
        return cache.put(key, value);
    }

    @Override
    public V remove(K key) {
        return cache.remove(key);
    }

    @Override
    public void clear() {
        cache.evictAll();
    }
}
