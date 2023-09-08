package taboola

class PlacementInfo {

    class THProperties(pageLink: String)  {
        val placementName = "Below Article Thumbnails"
        val sourceType = "text"
        val pageType = "article"
        val pageUrl = pageLink
        val targetType = "mix"
        val mode = "thumbnails-a"
    }
    class WidgetProperties  {
        val placementName = "Widget without video"
        val sourceType = "text"
        val pageType = "article"
        val pageUrl = "https://blog.taboola.com"
        val targetType = "mix"
        val mode = "thumbs-feed-01"
    }

    class ClassicFeedProperties {
        val placementName = "Feed with video"
        val sourceType = "text"
        val pageType = "article"
        val pageUrl = "https://blog.taboola.com"
        val targetType = "mix"
        val mode = "thumbs-feed-01"
    }

    class NativeFeedProperties {
        val placementName = "list_item"
        val sourceType = "text"
        val pageType = "article"
        val pageUrl = "https://blog.taboola.com"
        val targetType = "mix"
        val mode = "thumbs-feed-01"
    }

    class WebFeedProperties  {
        val placementName = "Feed without video"
        val sourceType = "text"
        val pageType = "article"
        val pageUrl = "https://blog.taboola.com"
        val targetType = "mix"
        val mode = "thumbs-feed-01"
    }

    // Static access
    companion object  {
        fun widgetProperties() = WidgetProperties()
        fun classicFeedProperties() = ClassicFeedProperties()
        fun nativeFeedProperties() = NativeFeedProperties()
        fun webFeedProperties() = WebFeedProperties()
    }


}