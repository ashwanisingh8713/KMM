package data.datasource

import kotlinx.serialization.Serializable

/**
 * Created by Ashwani Kumar Singh on 26,July,2023.
 */

@Serializable
class SectionRequestBody(
    val device: String = "android",
    val api_key: String = "hindu@9*M",
    val app_version: String = "78",
    val os_version: String = "29"
)
