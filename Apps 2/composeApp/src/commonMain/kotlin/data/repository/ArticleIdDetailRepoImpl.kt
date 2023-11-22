package data.repository

import domain.mapper.ArticleMapper
import domain.model.ArticleIdDetail
import domain.repository.ArticleIdDetailRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

/**
 * Created by Ashwani Kumar Singh on 20,November,2023.
 */
class ArticleIdDetailRepoImpl(private val endPoint: String,
                              private val httpClient: HttpClient): ArticleIdDetailRepo {
    override suspend fun getArticleDetail(articleId: Int): ArticleMapper {
        return requestOfArticleIdDetail(articleId)
    }

    private suspend fun requestOfArticleIdDetail(articleId: Int): ArticleMapper {
        val httpResponse = httpClient.get("$endPoint$articleId") {
            contentType(ContentType.Application.Json)
        }
        val content = httpResponse.body<ArticleIdDetail>()
        val data = content.data
        val article = data[0]
        var mapper = ArticleMapper(
            id =article.aid.toString(),
            aid = article.aid.toString(),
            secName = article.sname,
            secId = article.sid,
            pd = article.pd,
            au = article.au,
            ti = article.ti,
            de = article.de,
            al = article.al,
            banner = if (article.me.isNotEmpty()) article.me[0].im else "",
            thumb = article.im_thumbnail,
            media = article.me.toString(),
            le = article.le,
            articleType = article.articleType,
            avUrl = article.al,
            location = article.location,
        )

        return mapper
    }
}