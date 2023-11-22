package domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Created by Ashwani Kumar Singh on 20,November,2023.
 */
@Serializable
data class ArticleIdDetail(
    val data: List<ArticleIdData>,
    val s: Int,
    val status: Int
)
@Serializable
data class ArticleIdData(
    val add_pos: Int,
    val aid: Int,
    val al: String,
    val articleType: String,
    val au: String,
    val audioLink: String,
    val de: String,
    val gmt: String,
    val hi: Int,
    val im_thumbnail: String,
    val le: String,
    val location: String,
    val me: List<Me>,
    val od: String,
    val opid: Int,
    val parentId: String,
    val parentName: String,
    val pd: String,
    @Transient
    val rn: List<Any>? = null,
    @Transient
    val sections: List<Any>? = null,
    val short_de: String,
    val sid: String,
    val sname: String,
    val ti: String,
    val vid: String,
    val video_url: String,
    val youtube_video_id: String
)

