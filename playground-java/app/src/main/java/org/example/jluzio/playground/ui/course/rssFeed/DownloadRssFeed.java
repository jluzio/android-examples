package org.example.jluzio.playground.ui.course.rssFeed;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import org.example.jluzio.playground.data.remote.response.Feed;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;
import java.net.URL;

public class DownloadRssFeed extends AsyncTask<String, Void, Feed> {
    private static final String TAG = "DownloadRssFeed";
    private RecyclerView output;
    private TextView statusOutput;

    public DownloadRssFeed(RecyclerView output, TextView statusOutput) {
        this.output = output;
        this.statusOutput = statusOutput;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "onPreExecute: Starting task...");
        statusOutput.setText("Loading...");
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
    protected void onPostExecute(Feed feed) {
        super.onPostExecute(feed);
        statusOutput.setText("Feed loaded, refreshing!");
        Log.d(TAG, "onPostExecute: Feed loaded, refreshing!");
        FeedAdapter feedAdapter = (FeedAdapter) output.getAdapter();
        feedAdapter.setFeed(feed);
        feedAdapter.notifyDataSetChanged();
        statusOutput.setText("Feed refreshed!");
    }

    @Override
    protected void onCancelled(Feed feed) {
        super.onCancelled(feed);
        Log.d(TAG, "onCancelled: Task cancelled!");
        statusOutput.setText("Loading cancelled!");
    }
}
