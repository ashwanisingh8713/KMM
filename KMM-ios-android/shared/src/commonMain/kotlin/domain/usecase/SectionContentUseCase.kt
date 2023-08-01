package domain.usecase

import domain.model.SectionContent
import domain.repository.SectionContentRepo
import domain.usecase.base.UseCase

/**
 * Created by Ashwani Kumar Singh on 01,August,2023.
 */
class SectionContentUseCase(private val sectionContentRepo: SectionContentRepo): UseCase<SectionContent, Any>() {
    override suspend fun run(params: Any?): SectionContent {
        return sectionContentRepo.getSectionContent(params)
    }
}