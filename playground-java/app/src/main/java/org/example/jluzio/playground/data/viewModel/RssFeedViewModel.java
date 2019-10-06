package org.example.jluzio.playground.data.viewModel;

import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import org.example.jluzio.playground.data.cache.Cache;
import org.example.jluzio.playground.data.cache.Caches;

public class RssFeedViewModel extends ViewModel {
    private FeedId feedId;
    private int feedLimit = 10;
    private Cache<String,Bitmap> imageCache;

    public RssFeedViewModel() {
        imageCache = Caches.createMaxNumberItemsMemoryCache(20);
    }

    public FeedId getFeedId() {
        return feedId;
    }

    public void setFeedId(FeedId feedId) {
        this.feedId = feedId;
    }

    public int getFeedLimit() {
        return feedLimit;
    }

    public void setFeedLimit(int feedLimit) {
        this.feedLimit = feedLimit;
    }

    public Cache<String, Bitmap> getImageCache() {
        return imageCache;
    }

    public void setImageCache(Cache<String, Bitmap> imageCache) {
        this.imageCache = imageCache;
    }
}
