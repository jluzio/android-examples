package org.example.jluzio.playground.ui.course;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
    private CacheHandler cacheHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_feed);

        cacheHandler = CacheHandler.createMaxNumberItemsCache(20);

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

        feedListView.setAdapter(new FeedAdapter(null, cacheHandler));

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
                output.setAdapter(new FeedAdapter(feed, cacheHandler));
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
        private CacheHandler cacheHandler;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class FeedEntryViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public ConstraintLayout entryLayout;
            public TextView nameTextView;
            public TextView artistTextView;
            public TextView releaseDateTextView;
            public ImageView entryImageView;


            public FeedEntryViewHolder(ConstraintLayout entryLayout) {
                super(entryLayout);
                this.entryLayout = entryLayout;
                this.nameTextView = entryLayout.findViewById(R.id.nameTextView);
                this.artistTextView = entryLayout.findViewById(R.id.artistTextView);
                this.releaseDateTextView = entryLayout.findViewById(R.id.releaseDateTextView);
                this.entryImageView = entryLayout.findViewById(R.id.entryImageView);
            }
        }

        public FeedAdapter(Feed feed, CacheHandler cacheHandler) {
            this.feed = feed;
            this.cacheHandler = cacheHandler;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public FeedEntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // https://developer.android.com/guide/topics/ui/layout/recyclerview.html#java
            ConstraintLayout entryLayout = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rss_feed_entry, parent, false);

            FeedEntryViewHolder viewHolder = new FeedEntryViewHolder(entryLayout);
            return viewHolder;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(FeedEntryViewHolder holder, int position) {
            FeedEntry feedEntry = feed.getEntries().get(position);
//            String feedEntryLabel = String.format("%s | artist: %s | summary: %s", feedEntry.getName(), feedEntry.getArtist(), feedEntry.getSummary());
            holder.nameTextView.setText(feedEntry.getName());
            holder.artistTextView.setText(feedEntry.getArtist());
            holder.releaseDateTextView.setText(feedEntry.getReleaseDate());

            if (!feedEntry.getImages().isEmpty()) {
                FeedEntry.Image image = feedEntry.getImages().get(0);
                DownloadImageTask downloadImageTask = new DownloadImageTask(holder.entryImageView, cacheHandler);
                downloadImageTask.execute(image.getUrl());
            } else {
                holder.entryImageView.setImageResource(R.drawable.ic_menu_gallery);
            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return feed == null ? 0 : feed.getEntries().size();
        }
    }

    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private static final String TAG = "DownloadImageTask";
        private ImageView imageView;
        private CacheHandler cacheHandler;

        public DownloadImageTask(ImageView imageView, CacheHandler cacheHandler) {
            this.imageView = imageView;
            this.cacheHandler = cacheHandler;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Log.d(TAG, "doInBackground: retrieving image " + url);

            Bitmap image = cacheHandler.get(url);
            if (image != null) {
                Log.d(TAG, "doInBackground: image in cache " + url);
            } else {
                try (InputStream in = new java.net.URL(url).openStream()) {
                    image = BitmapFactory.decodeStream(in);
                    Log.d(TAG, "doInBackground: downloaded image " + url);
                    cacheHandler.put(url, image);
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground: error for image " + url, e);
                }
            }
            return image;
        }

        protected void onPostExecute(Bitmap image) {
            imageView.setImageBitmap(image);
        }
    }

    public static class CacheHandler {
        private LruCache<String, Bitmap> cache;
        private Object lock = new Object();

        public CacheHandler(LruCache<String, Bitmap> cache) {
            this.cache = cache;
        }

        public static CacheHandler createMaxNumberItemsCache(int maxSize) {
            LruCache<String, Bitmap> memoryCache = new LruCache<>(maxSize);
            return new CacheHandler(memoryCache);
        }

        public static CacheHandler createMaxContentsSizeCache(int maxSize) {
            // Get max available VM memory, exceeding this amount will throw an
            // OutOfMemory exception. Stored in kilobytes as LruCache takes an
            // int in its constructor.
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;

            LruCache<String, Bitmap> memoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    // The cache size will be measured in kilobytes rather than
                    // number of items.
                    return bitmap.getByteCount() / 1024;
                }
            };
            return new CacheHandler(memoryCache);
        }

        public void put(String key, Bitmap value) {
            synchronized (lock) {
                cache.put(key, value);
            }
        }

        public Bitmap get(String key) {
            synchronized (lock) {
                return cache.get(key);
            }
        }
    }
}
