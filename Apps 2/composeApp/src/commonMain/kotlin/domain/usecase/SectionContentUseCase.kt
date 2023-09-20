package domain.usecase

import domain.mapper.ArticleMapper
import domain.repository.SectionContentRepo
import domain.usecase.base.UseCase

/**
 * Created by Ashwani Kumar Singh on 01,August,2023.
 */
class SectionContentUseCase(private val sectionContentRepo: SectionContentRepo): UseCase<List<ArticleMapper>, Any>() {
    override suspend fun run(params: Any?): List<ArticleMapper> {
        return sectionContentRepo.getSectionContent(params)
    }
}