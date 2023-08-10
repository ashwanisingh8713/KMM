package domain.repository

import domain.model.SectionContent

/**
 * Created by Ashwani Kumar Singh on 01,August,2023.
 */
interface SectionContentRepo {
    suspend fun getSectionContent(params: Any?): SectionContent
}