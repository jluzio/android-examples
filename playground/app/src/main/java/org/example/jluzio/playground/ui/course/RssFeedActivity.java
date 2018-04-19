package org.example.jluzio.playground.ui.course;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.example.jluzio.playground.R;
import org.example.jluzio.playground.data.remote.response.Feed;
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
        //feedTextView = findViewById(R.id.feedTextView);
        feedListView = findViewById(R.id.feedListView);
        statusTextView = findViewById(R.id.statusTextView);

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
            //output.setAdapter(getListAdapter(feed));
            statusOutput.setText("Task successful!");
        }

        @Override
        protected void onCancelled(Feed feed) {
            super.onCancelled(feed);
            Log.d(TAG, "onCancelled: Task cancelled!");
            statusOutput.setText("Task cancelled!");
        }
    }

    private class FeedAdapter extends RecyclerView.Adapter {
        private Feed feed;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // https://developer.android.com/guide/topics/ui/layout/recyclerview.html#java
//            TextView v = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.rss_feed_entry, parent, false);

            RecyclerView.ViewHolder vh = null;
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return feed.getEntries().size();
        }
    }
}
