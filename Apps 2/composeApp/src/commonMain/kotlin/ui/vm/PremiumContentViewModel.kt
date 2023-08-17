package ui.vm

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import domain.model.PremiumContent
import domain.usecase.PremiumContentUseCase
import domain.usecase.base.UseCaseResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 16,August,2023.
 */
class PremiumContentViewModel(private val premiumContentUseCase: PremiumContentUseCase):
    KoinComponent, ScreenModel {

    private val _premiumArticle = MutableStateFlow<PremiumContent?>(null)
    val successPremiumArticle: StateFlow<PremiumContent?> get() = _premiumArticle

    private val _premiumArticleLoading = MutableStateFlow<Boolean>( true)
    val premiumArticleLoading: StateFlow<Boolean> get() = _premiumArticleLoading

    private val _premiumArticleError = MutableStateFlow<String?>( null)
    val premiumArticleError: StateFlow<String?> get() = _premiumArticleError

fun makePremiumContentApiRequest() {
        premiumContentUseCase.invoke(scope = coroutineScope, params = null, onResult = object :
            UseCaseResponse<PremiumContent> {
            override fun onSuccess(content: PremiumContent) {
                println("Request is successful :: $content")
                _premiumArticle.value =  content
            }

            override fun onError(apiError: String) {
                println("Request is Failed :: $apiError")
                _premiumArticleError.value = "PremiumArticleLoading -> $apiError"
            }

            override fun onLoading(isLoading: Boolean) {
                _premiumArticleLoading.value = isLoading
            }
        })
    }


}