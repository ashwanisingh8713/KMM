package ui.vm

import com.rickclephas.kmm.viewmodel.KMMViewModel
import domain.usecase.SectionListUseCase
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import data.datasource.SectionContentRequestBody
import data.datasource.SectionListRequestBody
import domain.model.SectionContent
import domain.model.SectionList
import domain.usecase.SectionContentUseCase
import domain.usecase.base.UseCaseResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by Ashwani Kumar Singh on 26,July,2023.
 */
class SectionListViewModel(private val sectionListUseCase: SectionListUseCase, val sectionContentUseCase: SectionContentUseCase): KMMViewModel() {

    val _sectionList = MutableStateFlow<SectionList?>(viewModelScope, null)
    @NativeCoroutinesState
    val successSectionList: StateFlow<SectionList?> get() = _sectionList


    private val _sectionContentMapState = MutableStateFlow<Map<String, SectionContent>?>(viewModelScope, null)
    val sectionContentMapState: StateFlow<Map<String, SectionContent>?> get() = _sectionContentMapState

    private val _sectionContentState = MutableStateFlow<SectionContent?>(viewModelScope, null)
    @NativeCoroutinesState
    val sectionContentState: StateFlow<SectionContent?> get() = _sectionContentState

    /*val _sectionContentState = MutableSharedFlow<SectionContent?>()
    @NativeCoroutinesState
    val sectionContentState: StateFlow<SectionContent?> get() = _sectionContentState*/

    private val _sectionContentLoading = MutableStateFlow<Boolean>(viewModelScope, true)
    @NativeCoroutinesState
    val sectionContentLoading: StateFlow<Boolean> get() = _sectionContentLoading

    private val _sectionContentError = MutableStateFlow<String?>(viewModelScope, null)
    @NativeCoroutinesState
    val sectionContentError: StateFlow<String?> get() = _sectionContentError

    fun makeSectionListApiRequest() {
        val params = SectionListRequestBody(device = "android", api_key = "hindu@9*M", app_version = 78, os_version = 29)
        sectionListUseCase.invoke(scope = viewModelScope.coroutineScope, params, onResult = object : UseCaseResponse<SectionList> {
            override fun onSuccess(content: SectionList) {
                println("Request is successful :: $content")
                _sectionList.update { content }
            }

            override fun onError(apiError: String) {
                println("Request is Failed :: $apiError")
            }

            override fun onLoading(isLoading: Boolean) {
                println("Request is Loading :: $isLoading")
            }

        })
    }

    fun makeSectionContentApiRequest(secId: Int, secName: String, type:String, page: Int = 1, lut: Int = 0) {
        println("makeSectionContentApiRequest - ViewModel :: secId= $secId, secName= $secName, type= $type")
        val params = SectionContentRequestBody(device = "android", api_key = "hindu@9*M", app_version = 78,
            os_version = 29, id = secId, lut = 0, type = type, page = page )
        sectionContentUseCase.invoke(scope = viewModelScope.coroutineScope, params, onResult = object : UseCaseResponse<SectionContent> {
            override fun onSuccess(content: SectionContent) {
                _sectionContentState.update { content }
            }

            override fun onError(apiError: String) {
                _sectionContentError.update { "$secName -> $apiError" }
            }

            override fun onLoading(isLoading: Boolean) {
                _sectionContentLoading.update { isLoading }
            }
        })
    }

    fun clearSectionContent() {
        _sectionContentState.update { null }
    }

}