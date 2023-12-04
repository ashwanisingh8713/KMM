package domain.repository

import domain.mapper.ArticleMapper


/**
 * Created by Ashwani Kumar Singh on 08,September,2023.
 */
interface ArticleIdDetailRepo {
    suspend fun getArticleDetail(articleId: Int): ArticleMapper
}
