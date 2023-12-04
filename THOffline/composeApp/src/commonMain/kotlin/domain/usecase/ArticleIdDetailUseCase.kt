package domain.usecase

import domain.mapper.ArticleMapper
import domain.model.ArticleIdDetail
import domain.repository.ArticleIdDetailRepo
import domain.usecase.base.UseCase

/**
 * Created by Ashwani Kumar Singh on 08,September,2023.
 */
class ArticleIdDetailUseCase(private val articleDetailRepo: ArticleIdDetailRepo):
    UseCase<ArticleMapper, Any>() {
    override suspend fun run(params: Any?): ArticleMapper {
        return articleDetailRepo.getArticleDetail(params as Int)
    }

}