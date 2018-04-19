package org.example.jluzio.playground.ui.course;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.example.jluzio.playground.R;
import org.example.jluzio.playground.data.remote.response.Feed;
import org.example.jluzio.playground.data.remote.response.FeedEntry;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;
import java.net.URL;

public class RssFeedActivity extends AppCompatActivity {
    private static final String TAG = "RssFeedActivity";

    private String feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml";
    private DownloadData currentTask;
    private Button loadFeedBtn;
    private Button cancelLoadFeedBtn;
    private RecyclerView feedListView;
    private TextView statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_feed);

        loadFeedBtn = findViewById(R.id.loadFeedBtn);
        cancelLoadFeedBtn = findViewById(R.id.cancelLoadFeedBtn);
        statusTextView = findViewById(R.id.statusTextView);

        feedListView = findViewById(R.id.feedListView);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        feedListView.setHasFixedSize(true);
        // use a linear layout manager
        feedListView.setLayoutManager(new LinearLayoutManager(this));

        feedListView.setAdapter(new FeedAdapter(null));

        loadFeedBtn.setOnClickListener( view -> {
            currentTask = new DownloadData(feedListView, statusTextView);
            currentTask.execute(feedUrl);
        });
        cancelLoadFeedBtn.setOnClickListener( view -> {
            if (currentTask != null && !currentTask.isCancelled()) {
                currentTask.cancel(true);
            }
        });
    }

    private class DownloadData extends AsyncTask<String, Void, Feed> {
        private RecyclerView output;
        private TextView statusOutput;
        private boolean replaceAdapter = false;

        public DownloadData(RecyclerView output, TextView statusOutput) {
            this.output = output;
            this.statusOutput = statusOutput;
        }

        @Override
        protected Feed doInBackground(String... urls) {
            String urlSpec = urls[0];
            Log.d(TAG, "doInBackground: start");
            Feed feed = null;
            try (InputStream dataInputStream = new URL(urlSpec).openStream()) {
                Serializer serializer = new Persister();
                feed = serializer.read(Feed.class, dataInputStream);
            } catch (Exception e) {
                Log.e(TAG, "doInBackground: error", e);
            }
            return feed;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: Starting task...");
            statusOutput.setText("Starting task...");
        }

        @Override
        protected void onPostExecute(Feed feed) {
            super.onPostExecute(feed);
            Log.d(TAG, "onPostExecute: Task successful!");
            if (replaceAdapter) {
                output.setAdapter(new FeedAdapter(feed));
                output.invalidate();
            } else {
                FeedAdapter feedAdapter = (FeedAdapter) output.getAdapter();
                feedAdapter.feed = feed;
                feedAdapter.notifyDataSetChanged();
            }
            statusOutput.setText("Task successful!");
        }

        @Override
        protected void onCancelled(Feed feed) {
            super.onCancelled(feed);
            Log.d(TAG, "onCancelled: Task cancelled!");
            statusOutput.setText("Task cancelled!");
        }
    }

    public static class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedEntryViewHolder> {
        private Feed feed;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class FeedEntryViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView textView;
            public FeedEntryViewHolder(TextView textView) {
                super(textView);
                this.textView = textView;
            }
        }

        public FeedAdapter(Feed feed) {
            this.feed = feed;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public FeedEntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // https://developer.android.com/guide/topics/ui/layout/recyclerview.html#java
            TextView textView = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rss_feed_entry, parent, false);

            FeedEntryViewHolder viewHolder = new FeedEntryViewHolder(textView);
            return viewHolder;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(FeedEntryViewHolder holder, int position) {
            FeedEntry feedEntry = feed.getEntries().get(position);
            String feedEntryLabel = String.format("%s | artist: %s | summary: %s", feedEntry.getName(), feedEntry.getArtist(), feedEntry.getSummary());
            holder.textView.setText(feedEntryLabel);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return feed == null ? 0 : feed.getEntries().size();
        }
    }
}
