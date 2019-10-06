package org.example.jluzio.playground.data.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

public class Caches {

    public static <K, V> Cache<K, V> createMaxNumberItemsMemoryCache(int maxSize) {
        LruCache<K, V> lruCache = new LruCache<>(maxSize);
        return new LruCacheWrapper(lruCache);
    }

    public static <K, V> Cache<K, V> createMaxContentsSizeMemoryCache(int maxSize) {
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
        return new LruCacheWrapper(lruCache);
    }

}
