package org.example.jluzio.playground.ui.course;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.example.jluzio.playground.R;
import org.example.jluzio.playground.data.remote.response.Feed;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;

public class RssFeedActivity extends AppCompatActivity {
    private static final String TAG = "RssFeedActivity";

    private String feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml";
    private DownloadData currentTask;
    private Button loadFeedBtn;
    private Button cancelLoadFeedBtn;
    private TextView feedTextView;
    private TextView statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_feed);

        loadFeedBtn = findViewById(R.id.loadFeedBtn);
        cancelLoadFeedBtn = findViewById(R.id.cancelLoadFeedBtn);
        feedTextView = findViewById(R.id.feedTextView);
        statusTextView = findViewById(R.id.statusTextView);

        loadFeedBtn.setOnClickListener( view -> {
            currentTask = new DownloadData(feedTextView, statusTextView);
            currentTask.execute(feedUrl);
        });
        cancelLoadFeedBtn.setOnClickListener( view -> {
            if (currentTask != null && !currentTask.isCancelled()) {
                currentTask.cancel(true);
            }
        });
    }

    private class DownloadData extends AsyncTask<String, Void, Void> {
        private TextView output;
        private TextView statusOutput;

        public DownloadData(TextView output, TextView statusOutput) {
            this.output = output;
            this.statusOutput = statusOutput;
        }

        @Override
        protected Void doInBackground(String... urls) {
            String urlSpec = urls[0];
            Log.d(TAG, "doInBackground: start");
            try (InputStream dataInputStream = new URL(urlSpec).openStream()) {
                Serializer serializer = new Persister();
                Feed feed = serializer.read(Feed.class, dataInputStream);
                Log.d(TAG, "doInBackground: loaded and parsed");

                StringWriter outputWriter = new StringWriter();
                serializer.write(feed, outputWriter);
                output.setText(outputWriter.toString());
                Log.d(TAG, "doInBackground: output finished");
            } catch (Exception e) {
                Log.e(TAG, "doInBackground: error", e);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: Starting task...");
            statusOutput.setText("Starting task...");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "onPostExecute: Task successful!");
            statusOutput.setText("Task successful!");
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
            Log.d(TAG, "onCancelled: Task cancelled!");
            statusOutput.setText("Task cancelled!");
        }
    }
}
