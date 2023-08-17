package domain.usecase

import domain.model.PremiumContent
import domain.model.SectionContent
import domain.usecase.base.UseCase

/**
 * Created by Ashwani Kumar Singh on 16,August,2023.
 */
class PremiumContentUseCase(
    private val premiumContentRepo: domain.repository.PremiumContentRepo): UseCase<PremiumContent, Any>() {

    override suspend fun run(params: Any?): PremiumContent {
        return premiumContentRepo.getPremiumContent(params)
    }
}