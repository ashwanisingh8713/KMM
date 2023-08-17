package domain.model

import kotlinx.serialization.Serializable

/**
 * Created by Ashwani Kumar Singh on 16,August,2023.
 */

@Serializable
data class PremiumContent(
    val data: List<PremiumData>
)

@Serializable
data class PremiumData(
    val articleId: Int,
    val articleLink: String,
    val articleType: String,
    val articletags: List<String>,
    val author: List<String>,
    val description: String,
    val gmt: String,
    val leadText: String,
    val location: String,
    val media: List<Media>,
    val originalDate: String,
    val publishedDate: String,
    val sectionName: String,
    val shortDescription: String,
    val thumbnailUrl: String,
    val timetoRead: String,
    val title: String,
    val videoId: String,
    val youtubeVideoId: String
)
@Serializable
data class Media(
    val caption: String,
    val image: String
)