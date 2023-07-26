package domain.model

import kotlinx.serialization.Serializable


/**
 * Created by Ashwani Kumar Singh on 26,July,2023.
 */

@Serializable
data class SectionList(
    val data: Data? = null
)

@Serializable
data class Data(
    val BLink: List<BLink>,
    val Portfolio: List<Portfolio>,
    val date: Int,
    val home: Home,
    val section: List<Section>
)

@Serializable
data class BLink(
    val custom: Boolean,
    val customScreen: Int,
    val customScreenPri: Int,
    val explorePriority: Int,
    val image: String,
    val image_v2: String,
    val link: String,
    val overrideExplore: Int,
    val overridePriority: Int,
    val parentId: String,
    val priority: Int,
    val secColorRgb: String,
    val secId: Int,
    val secName: String,
    val show_on_burger: Boolean,
    val show_on_explore: Boolean,
    val subSections: List<Any>,
    val type: String,
    val webLink: String
)

@Serializable
data class Portfolio(
    val custom: Boolean,
    val customScreen: Int,
    val customScreenPri: Int,
    val explorePriority: Int,
    val image: String,
    val image_v2: String,
    val link: String,
    val overrideExplore: Int,
    val overridePriority: Int,
    val parentId: String,
    val priority: Int,
    val secColorRgb: String,
    val secId: Int,
    val secName: String,
    val show_on_burger: Boolean,
    val show_on_explore: Boolean,
    val subSections: List<Any>,
    val type: String,
    val webLink: String
)

@Serializable
data class Home(
    val banner: Banner,
    val explore: List<Explore>,
    val personalize: List<Personalize>,
    val staticPageUrl: StaticPageUrl,
    val widget: List<Widget>
)

@Serializable
data class Section(
    val custom: Boolean,
    val customScreen: Int,
    val customScreenPri: Int,
    val explorePriority: Int,
    val image: String,
    val image_v2: String,
    val isNew: Boolean,
    val link: String,
    val overrideExplore: Int,
    val overridePriority: Int,
    val parentId: Int,
    val priority: Int,
    val secColorRgb: String,
    val secId: Int,
    val secName: String,
    val show_on_burger: Boolean,
    val show_on_explore: Boolean,
    val staticPageUrl: StaticPageUrl,
    val subSections: List<SubSection>,
    val type: String,
    val webLink: String
)

@Serializable
data class Banner(
    val secId: Int,
    val secName: String,
    val type: String
)

@Serializable
data class Explore(
    val homePriority: Int,
    val image: String,
    val image_v2: String,
    val overridePriority: Int,
    val secId: Int,
    val secName: String,
    val type: String
)

@Serializable
data class Personalize(
    val homePriority: Int,
    val overridePriority: Int,
    val secId: Int,
    val secName: String,
    val type: String
)

@Serializable
data class StaticPageUrl(
    val isEnabled: Boolean,
    val lastUpdatedOn: String,
    val position: String,
    val sectionId: Int,
    val url: String,
    val youtubeId: String
)

@Serializable
data class Widget(
    val homePriority: Int,
    val overridePriority: Int,
    val parentSecId: Int,
    val secId: Int,
    val secName: String,
    val type: String,
    val viewAllCTA: Boolean
)

@Serializable
data class SubSection(
    val custom: Boolean,
    val customScreen: Int,
    val customScreenPri: Int,
    val explorePriority: Int,
    val image: String,
    val image_v2: String,
    val isNew: Boolean,
    val link: String,
    val overrideExplore: Int,
    val overridePriority: Int,
    val parentId: Int,
    val priority: Int,
    val secColorRgb: String,
    val secId: Int,
    val secName: String,
    val show_on_burger: Boolean,
    val show_on_explore: Boolean,
    val staticPageUrl: StaticPageUrl,
    val type: String,
    val webLink: String
)