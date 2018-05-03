package org.example.jluzio.playground.data.viewModel;

import android.arch.lifecycle.ViewModel;

public class RssFeedViewModel extends ViewModel {
    private FeedId currentFeedId = FeedId.SONGS;
    private int feedMaxItems = 10;

    public FeedId getCurrentFeedId() {
        return currentFeedId;
    }

    public void setCurrentFeedId(FeedId currentFeedId) {
        this.currentFeedId = currentFeedId;
    }

    public int getFeedMaxItems() {
        return feedMaxItems;
    }

    public void setFeedMaxItems(int feedMaxItems) {
        this.feedMaxItems = feedMaxItems;
    }
}
