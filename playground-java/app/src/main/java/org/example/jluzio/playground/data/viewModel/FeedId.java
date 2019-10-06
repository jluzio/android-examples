package org.example.jluzio.playground.data.viewModel;

public enum FeedId {
    SONGS("topsongs"),
    ALBUMS("topalbums"),
    APPS("topfreeapplications");

    private final String category;

    FeedId(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public String getFeedUrl(int limit) {
        return FeedInfo.getUrl(category, limit);
    }

    static class FeedInfo {
        private static final String URL_FORMAT = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/%s/limit=%s/xml";

        public static String getUrl(String category, int limit) {
            return String.format(URL_FORMAT, category, limit);
        }
    }

}
