package data.repository

import domain.model.SectionContent
import domain.repository.SectionContentRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

/**
 * Created by Ashwani Kumar Singh on 01,August,2023.
 */
class SectionContentRepoImpl(private val endPoint: String, private val httpClient: HttpClient) : SectionContentRepo {
    override suspend fun getSectionContent(params: Any?): SectionContent {
        return requestOfSectionContent(params)
    }

    private suspend fun requestOfSectionContent(params: Any?): SectionContent {
        val httpResponse = httpClient.post("$endPoint/section-content.php") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(params)
        }
        return httpResponse.body()
    }
}