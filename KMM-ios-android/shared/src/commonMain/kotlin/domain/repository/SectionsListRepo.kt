package domain.repository

/**
 * Created by Ashwani Kumar Singh on 25,July,2023.
 */

interface SectionsListRepo {
    suspend fun getSectionList(params: Any?):Any
}