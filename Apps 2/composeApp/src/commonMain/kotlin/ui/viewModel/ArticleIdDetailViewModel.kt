package ui.viewModel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import domain.mapper.ArticleMapper
import domain.usecase.ArticleIdDetailUseCase
import domain.usecase.base.UseCaseResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 20,November,2023.
 */
class ArticleIdDetailViewModel(private val articleIdDetailUseCase: ArticleIdDetailUseCase) : KoinComponent,
    ScreenModel {

    private val _articleIdDetailSuccess = MutableSharedFlow<ArticleMapper?>(extraBufferCapacity = 1)
    val articleIdDetailSuccess: SharedFlow<ArticleMapper?> get() = _articleIdDetailSuccess

    private val _premiumArticleLoading = MutableStateFlow<Boolean>( true)
    val articleIdDetailLoading: StateFlow<Boolean> get() = _premiumArticleLoading

    private val _premiumArticleError = MutableStateFlow<String?>( null)
    val articleIdDetailError: StateFlow<String?> get() = _premiumArticleError

    fun makArticleIdApiRequest(articleID: Int) {
        articleIdDetailUseCase.invoke(scope = coroutineScope, params = articleID, onResult = object :
            UseCaseResponse<ArticleMapper> {
            override fun onSuccess(content: ArticleMapper) {
                println("Request is successful :: $content")
                _articleIdDetailSuccess.tryEmit(content)
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