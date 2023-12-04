package domain.repository

import domain.model.PremiumContent
import domain.model.SectionContent

/**
 * Created by Ashwani Kumar Singh on 16,August,2023.
 */
interface PremiumContentRepo {
    suspend fun getPremiumContent(params: Any?): PremiumContent
}