package data.repository

import data.datasource.ApiRequest
import domain.model.SectionContent
import domain.repository.SectionContentRepo

/**
 * Created by Ashwani Kumar Singh on 01,August,2023.
 */
class SectionContentRepoImpl(private val apiRequest: ApiRequest) : SectionContentRepo {
    override suspend fun getSectionContent(params: Any?): SectionContent {
        return apiRequest.getSectionContent(params)
    }
}