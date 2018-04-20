package org.example.jluzio.playground.ui.course.rssFeed;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import org.example.jluzio.playground.R;
import org.example.jluzio.playground.data.cache.Cache;
import org.example.jluzio.playground.data.cache.Caches;

public class RssFeedActivity extends AppCompatActivity {
    private static final String TAG = "RssFeedActivity";

    private String feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=100/xml";
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
            currentTask = new DownloadRssFeed(feedListView, statusTextView);
            currentTask.execute(feedUrl);
        });
        cancelLoadFeedBtn.setOnClickListener( view -> {
            if (currentTask != null && !currentTask.isCancelled()) {
                currentTask.cancel(true);
            }
        });

    }

}
