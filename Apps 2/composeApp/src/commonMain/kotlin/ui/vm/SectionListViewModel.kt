package ui.vm

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.SectionListUseCase
import data.datasource.SectionContentRequestBody
import data.datasource.SectionListRequestBody
import domain.model.SectionContent
import domain.model.SectionList
import domain.usecase.SectionContentUseCase
import domain.usecase.base.UseCaseResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import ui.screens.util.ComposeTag

/**
 * Created by Ashwani Kumar Singh on 26,July,2023.
 */
class SectionListViewModel(private val sectionListUseCase: SectionListUseCase, val sectionContentUseCase: SectionContentUseCase):
    ScreenModel, KoinComponent {


    private val _sectionList = MutableStateFlow<SectionList>(SectionList())
    val successSectionList: StateFlow<SectionList?> get() = _sectionList

    private val _sectionListLoading = MutableStateFlow<Boolean>( true)
    val sectionListLoading: StateFlow<Boolean> get() = _sectionListLoading

    private val _sectionListError = MutableStateFlow<String?>( null)
    val sectionListError: StateFlow<String?> get() = _sectionListError

    private val _sectionContentState = MutableStateFlow<SectionContent?>( null)
    val sectionContentState: StateFlow<SectionContent?> get() = _sectionContentState

    private var contentMap = mutableMapOf<Int, SectionContent>()

    private val _sectionContentLoading = MutableStateFlow<Boolean>( true)
    val sectionContentLoading: StateFlow<Boolean> get() = _sectionContentLoading

    private val _sectionContentError = MutableStateFlow<String?>( null)
    val sectionContentError: StateFlow<String?> get() = _sectionContentError

    fun makeSectionListApiRequest() {
        val params = SectionListRequestBody(device = "android", api_key = "hindu@9*M", app_version = 78, os_version = 29)
        sectionListUseCase.invoke(scope = coroutineScope, params, onResult = object : UseCaseResponse<SectionList> {
            override fun onSuccess(content: SectionList) {
                println("Request is successful :: $content")
                _sectionList.update { content }
            }

            override fun onError(apiError: String) {
                println("Request is Failed :: $apiError")
                _sectionListError.update { "SectionListLoading -> $apiError" }
            }

            override fun onLoading(isLoading: Boolean) {
                println("Request is Loading :: $isLoading")
                _sectionListLoading.update { isLoading }
            }

        })
    }

    fun makeSectionContentApiRequest(secId: Int, secName: String, type:String, page: Int = 1, lut: Int = 0) {
        println("$ComposeTag: LaunchedEffect -> secId = $secId, secName = $secName, secType = $type")
        val params = SectionContentRequestBody(device = "android", api_key = "hindu@9*M", app_version = 78,
            os_version = 29, id = secId, lut = 0, type = type, page = page )
        sectionContentUseCase.invoke(scope = coroutineScope, params, onResult = object : UseCaseResponse<SectionContent> {
            override fun onSuccess(content: SectionContent) {
                println("makeSectionContentApiRequest - ViewModel :: secId= $secId, secName= $secName, type= $type")
                println("$ComposeTag: Success Result -> secId = ${content.data.sid}, secName = ${content.data.sname}")
                _sectionContentState.value = content
                contentMap[secId] = content
            }

            override fun onError(apiError: String) {
                if(apiError.isNotEmpty() && apiError.contains("Unable to resolve host")) {
                    val content = contentMap[secId]
                    _sectionContentState.value = content
                } else {
                    _sectionContentError.value = apiError
                }



            }

            override fun onLoading(isLoading: Boolean) {
                _sectionContentLoading.value = isLoading
            }
        })
    }

    fun clearSectionContent() {
        _sectionContentState.update { null }
    }

}