package data.repository

import domain.model.PremiumContent
import domain.model.SectionContent
import domain.repository.PremiumContentRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

/**
 * Created by Ashwani Kumar Singh on 16,August,2023.
 */
class PremiumContentRepoImpl(private val endPoint: String, private val httpClient: HttpClient): PremiumContentRepo{
    override suspend fun getPremiumContent(params: Any?): PremiumContent {
        return requestOfPremiumContent(params)
    }


    private suspend fun requestOfPremiumContent(params: Any?): PremiumContent {
        val httpResponse = httpClient.get("$endPoint") {
            contentType(ContentType.Application.Json)
        }
        return httpResponse.body<PremiumContent>()
    }
}