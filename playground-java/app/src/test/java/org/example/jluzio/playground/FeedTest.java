package org.example.jluzio.playground;

import com.google.common.collect.Lists;

import org.example.jluzio.playground.data.remote.response.Feed;
import org.example.jluzio.playground.data.remote.response.FeedEntry;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.net.URL;

public class FeedTest {

    @Test
//    @org.junit.Ignore
    public void testRead() throws Exception {
        // http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml
        System.out.println("home: " + System.getProperty("user.home"));
        URL resource = this.getClass().getClassLoader().getResource("rss_feed.xml");

        File source = new File(resource.getPath());

        Serializer serializer = new Persister();
        Feed feed = serializer.read(Feed.class, source);

        serializer.write(feed, System.out);

    }

    @Test
    public void testWrite() throws Exception {
        Feed feed = new Feed();
        feed.setEntries(Lists.newArrayList());

        FeedEntry entry1 = new FeedEntry();
        entry1.setName("name1");
        entry1.setArtist("artist");
        entry1.setSummary("summary1");
        entry1.setReleaseDate("1997-07-16T19:20:30.45+01:00");

        FeedEntry.Image image1 = new FeedEntry.Image();
        image1.setUrl("http://org.example/image.png");
        image1.setHeight("50");

        FeedEntry.Image image2 = new FeedEntry.Image();
        image2.setUrl("http://org.example/image-100.png");
        image2.setHeight("100");

        entry1.setImages(Lists.newArrayList(image1, image2));
        feed.getEntries().add(entry1);

        FeedEntry entry2 = new FeedEntry();
        entry2.setName("name2");
        feed.getEntries().add(entry2);

        Serializer serializer = new Persister();
        serializer.write(feed, System.out);

    }

}
