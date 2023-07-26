package domain.usecase

import domain.model.SectionList
import domain.repository.SectionsListRepo
import domain.usecase.base.UseCase

/**
 * Created by Ashwani Kumar Singh on 26,July,2023.
 */
class SectionListUseCase(private val sectionsListRepo: SectionsListRepo): UseCase<SectionList, Any>()  {

    override suspend fun run(params: Any?): SectionList {
        return sectionsListRepo.getSectionList(params)
    }


}