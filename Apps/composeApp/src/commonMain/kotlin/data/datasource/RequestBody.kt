package data.datasource

import kotlinx.serialization.Serializable

/**
 * Created by Ashwani Kumar Singh on 26,July,2023.
 */

@Serializable
class SectionListRequestBody(
    val device: String,
    val api_key: String,
    val app_version: Int,
    val os_version: Int
)

@Serializable
class SectionContentRequestBody(
    val device: String,
    val api_key: String,
    val app_version: Int,
    val os_version: Int,
    val id:Int,
    val lut: Int,
    val type: String,
    val page: Int,
)
