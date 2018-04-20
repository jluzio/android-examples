package org.example.jluzio.playground.ui.course.rssFeed;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.example.jluzio.playground.R;
import org.example.jluzio.playground.data.cache.Cache;
import org.example.jluzio.playground.data.cache.Caches;

public class RssFeedActivity extends AppCompatActivity {
    enum FeedId {
        SONGS("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml"),
        ALBUMS("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topalbums/limit=10/xml"),
        APPS("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");

        private final String feedUrl;

        FeedId(String feedUrl) {
            this.feedUrl = feedUrl;
        }

        public String getFeedUrl() {
            return feedUrl;
        }
    }

    private static final String TAG = "RssFeedActivity";
    private FeedId currentFeedId = FeedId.SONGS;
    private DownloadRssFeed currentTask;
    private Button loadFeedBtn;
    private Button cancelLoadFeedBtn;
    private RecyclerView feedListView;
    private TextView statusTextView;
    private Cache<String,Bitmap> imageCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_feed);

        imageCache = Caches.createMaxNumberItemsMemoryCache(20);

        loadFeedBtn = findViewById(R.id.loadFeedBtn);
        cancelLoadFeedBtn = findViewById(R.id.cancelLoadFeedBtn);
        statusTextView = findViewById(R.id.statusTextView);

        feedListView = findViewById(R.id.feedListView);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        feedListView.setHasFixedSize(true);
        // use a linear layout manager
        feedListView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        feedListView.addItemDecoration(itemDecoration);

        feedListView.setAdapter(new FeedAdapter(null, imageCache));

        loadFeedBtn.setOnClickListener( view -> {
            loadFeed(currentFeedId);
        });
        cancelLoadFeedBtn.setOnClickListener( view -> {
            cancelLoadFeed();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feeds_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.songsFeedMenuItem:
                currentFeedId = FeedId.SONGS;
                break;
            case R.id.albumsFeedMenuItem:
                currentFeedId = FeedId.ALBUMS;
                break;
            case R.id.appsFeedMenuItem:
                currentFeedId = FeedId.APPS;
                break;
        }
        loadFeed(currentFeedId);
        return true;
    }

    private void loadFeed(FeedId feedId) {
        cancelLoadFeed();
        currentTask = new DownloadRssFeed(feedListView, statusTextView);
        currentTask.execute(feedId.getFeedUrl());
    }

    private void cancelLoadFeed() {
        if (currentTask != null && !currentTask.isCancelled()) {
            currentTask.cancel(true);
        }
    }

}
