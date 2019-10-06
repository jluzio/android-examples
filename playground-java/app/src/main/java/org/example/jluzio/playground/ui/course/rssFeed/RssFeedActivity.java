package org.example.jluzio.playground.ui.course.rssFeed;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.example.jluzio.playground.R;
import org.example.jluzio.playground.data.cache.HttpResponseCache;
import org.example.jluzio.playground.data.viewModel.FeedId;
import org.example.jluzio.playground.data.viewModel.RssFeedViewModel;

public class RssFeedActivity extends AppCompatActivity {
    private static final String TAG = "RssFeedActivity";

    private RssFeedViewModel viewModel;
    private DownloadRssFeed currentTask;
    private Button loadFeedBtn;
    private Button cancelLoadFeedBtn;
    private RecyclerView feedListView;
    private TextView statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_feed);

        HttpResponseCache.instance().enable(this);

        viewModel = ViewModelProviders.of(this).get(RssFeedViewModel.class);

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

        feedListView.setAdapter(new FeedAdapter(null, viewModel.getImageCache()));

        statusTextView = findViewById(R.id.statusTextView);

        loadFeedBtn.setOnClickListener( view -> {
            loadFeed(viewModel.getFeedId());
        });
        cancelLoadFeedBtn.setOnClickListener( view -> {
            cancelLoadFeed();
        });

        // auto load if required (for example rotate screen)
        if (viewModel.getFeedId() != null) {
            loadCurrentFeed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: creating...");
        getMenuInflater().inflate(R.menu.feeds_menu, menu);
        int maxItemsId;
        if (viewModel.getFeedLimit() == 10) {
            maxItemsId = R.id.menu_size_10;
        } else {
            maxItemsId = R.id.menu_size_25;
        }
        menu.findItem(maxItemsId).setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FeedId feedId = null;
        Integer feedLimit = null;
        boolean changed = false;

        switch (item.getItemId()) {
            case R.id.menu_songsFeed:
                feedId = FeedId.SONGS;
                break;
            case R.id.menu_albumsFeed:
                feedId = FeedId.ALBUMS;
                break;
            case R.id.menu_appsFeed:
                feedId = FeedId.APPS;
                break;
            case R.id.menu_size_10:
                feedLimit = 10;
                if (!item.isChecked()) {
                    item.setChecked(true);
                }
                break;
            case R.id.menu_size_25:
                feedLimit = 25;
                if (!item.isChecked()) {
                    item.setChecked(true);
                }
                break;
            case R.id.menu_refresh:
                changed = true;
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        if (feedId != null) {
            changed = feedId != viewModel.getFeedId();
            viewModel.setFeedId(feedId);
        } else if (feedLimit != null) {
            changed = feedLimit.intValue() != viewModel.getFeedLimit();
            viewModel.setFeedLimit(feedLimit);
        }

        Log.d(TAG, String.format("onOptionsItemSelected: changed=%s | feedId=%s | feedLimit=%s | itemId=%s",
                changed, feedId, feedLimit, item.getItemId()));

        if (changed) {
            loadCurrentFeed();
        }
        return true;
    }

    private void loadCurrentFeed() {
        loadFeed(viewModel.getFeedId());
    }

    private void loadFeed(FeedId feedId) {
        cancelLoadFeed();
        Log.d(TAG, String.format("loadFeed: loading %s | %s", viewModel.getFeedId(), viewModel.getFeedLimit()));
        if (feedId != null) {
            currentTask = new DownloadRssFeed(feedListView, statusTextView);
            currentTask.execute(feedId.getFeedUrl(viewModel.getFeedLimit()));
        }
    }

    private void cancelLoadFeed() {
        if (currentTask != null && !currentTask.isCancelled()) {
            currentTask.cancel(true);
        }
    }

}
