package data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.logging.*
import io.ktor.http.HttpHeaders

/**
 * Created by Ashwani Kumar Singh on 25,July,2023.
 */
class ApiRequest {

    fun createHttpClient():HttpClient {
        return HttpClient() {
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
}