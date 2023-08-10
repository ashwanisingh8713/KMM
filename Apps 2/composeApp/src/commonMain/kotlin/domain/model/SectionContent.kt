package domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Created by Ashwani Kumar Singh on 01,August,2023.
 */
@Serializable
data class SectionContent(
    val data: SectionContentData
)
@Serializable
data class SectionContentData(
    val article: List<Article>,
    val da: String,
    val imc: Int,
    val media: List<Me>,
    val s: Int,
    val sid: Int,
    val sname: String,
    val yd: String
)
@Serializable
data class Article(
    val add_pos: Int,
    val aid: Int,
    val al: String,
    val articleType: String,
    val articletags: List<String> = emptyList(),
    val au: String,
    val audioLink: String,
    val bk: String,
    val comm_count: String,
    val de: String,
    val gmt: String,
    val hi: Int,
    val im_thumbnail: String,
    val im_thumbnail_v2: String = "",
    val isArticleRestricted: String = "",
    val is_audio: Boolean,
    val is_photo: Boolean,
    val is_rn: Boolean,
    val is_video: Boolean,
    val le: String,
    val location: String = "",
    val location_ios: String = "",
    val me: List<Me>,
    val od: String,
    val opid: Int,
    val parentId: String,
    val parentName: String,
    val pd: String,
    val pid: String,
    @Transient
    val rn: List<Article>? = null,
    @Transient
    val sections: List<Any>? = null,
    val short_de: String? = null,
    val sid: String,
    val sname: String,
    val ti: String,
    val vid: String,
    val youtube_video_id: String
)

@Serializable
data class Me(
    val ca: String,
    val im: String,
)