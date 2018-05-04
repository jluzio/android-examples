package org.example.jluzio.playground.data.cache;

import android.content.Context;
import android.util.Log;

import java.io.File;

public class HttpResponseCache {
    private static final String TAG = "HttpResponseCache";
    private static HttpResponseCache INSTANCE = new HttpResponseCache();
    private boolean enabled = false;

    public static HttpResponseCache instance() {
        return INSTANCE;
    }

    /**
     * https://developer.android.com/training/efficient-downloads/redundant_redundant
     * @param context
     */
    public void enable(Context context) {
        if (!enabled) {
            try {
                // You can cache non-sensitive data in the unmanaged external cache directory:
                File externalCacheDir = context.getExternalCacheDir();
                // Alternatively, you can use the managed, secure application cache.
                // Note that this internal cache may be flushed when the system is running low on available storage.
                File cacheDir = context.getCacheDir();

                long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
                File httpCacheDir = new File(externalCacheDir, "http");
                Class.forName("android.net.http.HttpResponseCache")
                        .getMethod("install", File.class, long.class)
                        .invoke(null, httpCacheDir, httpCacheSize);
            } catch (Exception httpResponseCacheNotAvailable) {
                Log.d(TAG, "HTTP response cache is unavailable.");
            }
            enabled = true;
        }
    }
}
