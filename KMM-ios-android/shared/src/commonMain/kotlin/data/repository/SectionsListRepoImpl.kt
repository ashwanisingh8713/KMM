package data.repository

import data.datasource.ApiRequest
import domain.model.SectionList
import domain.repository.SectionsListRepo

/**
 * Created by Ashwani Kumar Singh on 25,July,2023.
 */
class SectionsListRepoImpl(private val apiRequest: ApiRequest):SectionsListRepo {

    override suspend fun getSectionList(params: Any?): SectionList {
        return apiRequest.getSectionList()
    }
}