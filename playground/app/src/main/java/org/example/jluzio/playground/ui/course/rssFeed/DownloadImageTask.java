package org.example.jluzio.playground.ui.course.rssFeed;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.example.jluzio.playground.R;
import org.example.jluzio.playground.data.cache.Cache;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private static final String TAG = "DownloadImageTask";
    private ImageView imageView;
    private Cache<String,Bitmap> imageCache;

    public DownloadImageTask(ImageView imageView, Cache<String, Bitmap> imageCache) {
        this.imageView = imageView;
        this.imageCache = imageCache;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        imageView.setImageResource(R.drawable.ic_menu_gallery);
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Log.d(TAG, "doInBackground: retrieving image " + url);

        Bitmap image = imageCache.get(url);
        if (image != null) {
            Log.d(TAG, "doInBackground: image in cache " + url);
        } else {
            try (InputStream in = new java.net.URL(url).openStream()) {
                image = BitmapFactory.decodeStream(in);
                Log.d(TAG, "doInBackground: downloaded image " + url);
                imageCache.put(url, image);
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
