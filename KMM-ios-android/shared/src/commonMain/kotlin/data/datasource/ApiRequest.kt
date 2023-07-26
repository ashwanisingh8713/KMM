package data.datasource

import domain.model.SectionList
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Created by Ashwani Kumar Singh on 25,July,2023.
 */
class ApiRequest {

    companion object {
        val httpClient: HttpClient = HttpClient() {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })

            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.HEADERS
                filter { request ->
                    request.url.host.contains("ktor.io")
                }
                sanitizeHeader { header ->
                    header == HttpHeaders.Authorization
                }
            }

            engine {
                // this: HttpClientEngineConfig
                threadsCount = 4
                pipelining = true
            }
        }
    }

    suspend fun getSectionList():SectionList {
        val httpResponse = httpClient.post("https://app.thehindubusinessline.com/hinduBL/service/api_v2/sectionList_v4.php") {
            contentType(ContentType.Application.Json)
            setBody(SectionRequestBody())
        }
        val sectionList:SectionList = httpResponse.body()
        return sectionList
    }

}