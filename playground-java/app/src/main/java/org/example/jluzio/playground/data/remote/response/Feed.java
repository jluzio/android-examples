package org.example.jluzio.playground.data.remote.response;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false)
public class Feed {
    @ElementList(inline = true)
    private List<FeedEntry> entries;

    public List<FeedEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<FeedEntry> entries) {
        this.entries = entries;
    }
}
