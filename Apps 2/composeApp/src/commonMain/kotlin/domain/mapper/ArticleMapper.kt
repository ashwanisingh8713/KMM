package data.model

import kotlinx.serialization.Serializable

/**
 * Created by Ashwani Kumar Singh on 20,September,2023.
 */
@Serializable
data class ArticleMapper(val id: String, val aid: String, val secName: String? = null,
                         val secId: String? = null, val pd: String? = null, val au: String? = null, val ti: String? = null,
                         val de: String? = null, val al: String? = null, val banner: String? = null, val thumb: String? = null,
                         val media: String? = null, val le: String? = null, val articleType: String? = null, val avUrl: String? = null,
                         val location: String? = null) {
}

// create the mapper function for it
val articleMapper: ((id: String, aid: String, secName: String?,
                     secId: String?, pd: String?, au: String?, ti: String?, de: String?,
                     al: String?, banner: String?, thumb: String?, media: String?,
                     le: String?, articleType: String?, avUrl: String? ,location: String?) -> ArticleMapper) =
    { id, aid, secName, secId, pd, au, ti, de, al, banner, thumb, media, le, articleType, avUrl, location -> ArticleMapper(id, aid, secName, secId, pd, au, ti, de, al, banner, thumb, media, le, articleType, avUrl, location) }
