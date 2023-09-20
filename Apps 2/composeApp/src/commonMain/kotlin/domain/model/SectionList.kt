package domain.model

import domain.mapper.ArticleMapper
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


/**
 * Created by Ashwani Kumar Singh on 26,July,2023.
 */

@Serializable
data class SectionList(
    val data: Data? = null
)
@Serializable
data class Data(
    val date: Int,
    val home: Home,
    val section: List<Section>
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
    @Transient
    val explorePriority: Int = 0,
    val image: String,
    val image_v2: String? = "",
    val isNew: Boolean,
    @Transient
    val link: String? = null,
    @Transient
    val overrideExplore: Int = 0,
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
    @Transient
    val webLink: String? = null
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
    @Transient
    val lastUpdatedOn: String? = null,
    val position: String,
    val sectionId: Int,
    val url: String,
    @Transient
    val youtubeId: String? = null
)
@Serializable
data class Widget(
    val homePriority: Int,
    val overridePriority: Int,
    val parentSecId: Int,
    val secId: Int,
    val secName: String,
    val type: String,
    val viewAllCTA: Boolean,
    var articles: MutableList<ArticleMapper>? = null,
)
@Serializable
data class SubSection(
    val custom: Boolean,
    val customScreen: Int,
    val customScreenPri: Int,
    @Transient
    val explorePriority: Int = 0,
    val image: String,
    val image_v2: String,
    val isNew: Boolean,
    @Transient
    val link: String? = null,
    @Transient
    val overrideExplore: Int = 0,
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