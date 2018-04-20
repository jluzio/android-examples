package org.example.jluzio.playground.ui.course.rssFeed;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.collect.Maps;

import org.example.jluzio.playground.R;
import org.example.jluzio.playground.data.cache.Cache;
import org.example.jluzio.playground.data.remote.response.Feed;
import org.example.jluzio.playground.data.remote.response.FeedEntry;

import java.util.Map;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedEntryViewHolder> {
    private Feed feed;
    private Cache<String,Bitmap> imageCache;
    private Map<FeedEntryViewHolder, DownloadImageTask> loadingTasks = Maps.newHashMap();

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

    public FeedAdapter(Feed feed, Cache<String,Bitmap> imageCache) {
        this.feed = feed;
        this.imageCache = imageCache;
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

        holder.nameTextView.setText(feedEntry.getName());
        holder.artistTextView.setText(feedEntry.getArtist());
        holder.releaseDateTextView.setText(feedEntry.getReleaseDate());

        if (!feedEntry.getImages().isEmpty()) {
            FeedEntry.Image image = feedEntry.getImages().get(0);
            DownloadImageTask previousDownloadImageTask = loadingTasks.get(holder);
            if (previousDownloadImageTask != null && previousDownloadImageTask.getStatus() != AsyncTask.Status.FINISHED) {
                previousDownloadImageTask.cancel(true);
            }
            DownloadImageTask downloadImageTask = new DownloadImageTask(holder.entryImageView, imageCache);
            downloadImageTask.execute(image.getUrl());
            loadingTasks.put(holder, downloadImageTask);
        } else {
            holder.entryImageView.setImageResource(R.drawable.ic_menu_gallery);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return feed == null ? 0 : feed.getEntries().size();
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Cache<String, Bitmap> getImageCache() {
        return imageCache;
    }

    public void setImageCache(Cache<String, Bitmap> imageCache) {
        this.imageCache = imageCache;
    }
}
