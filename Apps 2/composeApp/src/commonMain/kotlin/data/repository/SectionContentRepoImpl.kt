package data.repository

import daniel.avila.rnm.kmm.data_cache.sqldelight.SharedDatabase
import data.datasource.SectionContentRequestBody
import domain.mapper.ArticleMapper
import domain.model.Article
import domain.model.SectionContent
import domain.repository.SectionContentRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import util.getCurrentDateTimeInMilli

/**
 * Created by Ashwani Kumar Singh on 01,August,2023.
 */
class SectionContentRepoImpl(
    private val endPoint: String,
    private val httpClient: HttpClient,
    private val sharedDatabase: SharedDatabase
) : SectionContentRepo {
    override suspend fun getSectionContent(params: Any?): List<ArticleMapper> {
        return requestOfSectionContent(params)
    }

    private suspend fun requestOfSectionContent(params: Any?): List<ArticleMapper> {
        val intervalMillis = 90000
        val body = params as SectionContentRequestBody
        val secId = body.id.toString()


        sharedDatabase {
            val SectionLastUpdate = it.appDatabaseQueries.selectSectionLastUpdateTime(secId).executeAsOneOrNull()

            if (SectionLastUpdate != null) {
                val elapsedMillis = getCurrentDateTimeInMilli() - SectionLastUpdate.lastUpdateTime
                if (elapsedMillis > intervalMillis) {
                    val secArticles = mutableListOf<Article>()
                    var secName = ""
                    var resSecId = ""
                        // Make Api call
                    try {
                        val httpResponse = httpClient.post("$endPoint/section-content.php") {
                            contentType(ContentType.Application.Json)
                            accept(ContentType.Application.Json)
                            setBody(params)
                        }
                        val content = httpResponse.body<SectionContent>()
                        secName = content.data.sname
                        resSecId = content.data.sid.toString()
                        secArticles.addAll(content.data.article)
                    } catch (apiError: Exception) {
                        if(apiError.message?.contains("Unable to resolve host") == false) {
                            throw apiError
                        }
                    }



                    // Delete all articles of this section
                    it.transaction {
                        it.appDatabaseQueries.removeSecArticle(secId = resSecId)
                    }
                    // Insert all articles of this section
                    it.transaction {
                        secArticles.map { it1 ->
                            it.appDatabaseQueries.insertSecArticle(
                                aid = it1.aid.toString(),
                                secName = secName,
                                secId = resSecId,
                                pd = it1.pd,
                                au = it1.au,
                                ti = it1.ti,
                                de = it1.de,
                                al = it1.al,
                                banner = if (it1.me.isNotEmpty()) it1.me[0].im else "",
                                thumb = it1.im_thumbnail_v2,
                                media = it1.me.toString(),
                                le = it1.le,
                                articleType = it1.articleType,
                                avUrl = it1.al,
                                location = it1.location,
                            )
                        }
                    }
                    // Update last update time
                    it.transaction {
                        it.appDatabaseQueries.updateSectionLastUpdateTime(secId = secId,
                            lastUpdateTime = getCurrentDateTimeInMilli()
                        )
                    }

                } else {
                    // ToDo: Nothing to do
                }
            } // end of if -> SectionLastUpdate != null
            else {
                // Make Api call
                val httpResponse = httpClient.post("$endPoint/section-content.php") {
                    contentType(ContentType.Application.Json)
                    accept(ContentType.Application.Json)
                    setBody(params)
                }
                val content = httpResponse.body<SectionContent>()
                val secArticles = content.data.article

                it.transaction {
                    secArticles.map { it1 ->
                        it.appDatabaseQueries.insertSecArticle(
                            aid = it1.aid.toString(),
                            secName = content.data.sname,
                            secId = content.data.sid.toString(),
                            pd = it1.pd,
                            au = it1.au,
                            ti = it1.ti,
                            de = it1.de,
                            al = it1.al,
                            banner = if (it1.me.isNotEmpty()) it1.me[0].im else "",
                            thumb = it1.im_thumbnail_v2,
                            media = it1.me.toString(),
                            le = it1.le,
                            articleType = it1.articleType,
                            avUrl = it1.al,
                            location = it1.location,
                        )
                    }
                }

                // Insert last update time
                it.transaction {
                    it.appDatabaseQueries.insertSectionLastUpdateTime(secId = content.data.sid.toString(),
                        secName = content.data.sname,
                        lastUpdateTime = getCurrentDateTimeInMilli()
                    )
                }

            }
        }


        val articles = sharedDatabase {
            it.appDatabaseQueries.selectSecArticle(secId).executeAsList().map {
                ArticleMapper(
                    id = it.id.toString(),
                    aid = it.aid,
                    secName = it.secName,
                    secId = it.secId,
                    pd = it.pd,
                    au = it.au,
                    ti = it.ti,
                    de = it.de,
                    al = it.al,
                    banner = it.banner,
                    thumb = it.thumb,
                    media = it.media,
                    le = it.le,
                    articleType = it.articleType,
                    avUrl = it.avUrl,
                    location = it.location
                )

            }
        }
        return articles
    }
}