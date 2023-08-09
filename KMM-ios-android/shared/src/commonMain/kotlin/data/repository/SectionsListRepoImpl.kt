package data.repository

import data.datasource.ApiRequest
import domain.model.SectionList
import domain.repository.SectionsListRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

/**
 * Created by Ashwani Kumar Singh on 25,July,2023.
 */
class SectionsListRepoImpl(private val endPoint: String, private val httpClient: HttpClient):SectionsListRepo {

    override suspend fun getSectionList(params: Any?): SectionList {
        return requestOfSectionList(params)
    }

    private suspend fun requestOfSectionList(params: Any?):SectionList {
        val httpResponse = httpClient.post("$endPoint/sectionList_v4.php") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(params)
        }
        val sectionList:SectionList = httpResponse.body()
        return sectionList
    }
}