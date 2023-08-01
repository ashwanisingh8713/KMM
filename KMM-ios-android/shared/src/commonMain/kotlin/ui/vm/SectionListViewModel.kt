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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by Ashwani Kumar Singh on 26,July,2023.
 */
class SectionListViewModel(private val sectionListUseCase: SectionListUseCase, val sectionContentUseCase: SectionContentUseCase): KMMViewModel() {

    val _sectionList = MutableStateFlow<SectionList?>(viewModelScope, null)
    @NativeCoroutinesState
    val successSectionList: StateFlow<SectionList?> get() = _sectionList

    data class SectionContentState(
        var isSuccess : Boolean = false,
        var isLoading: Boolean = false,
        val sectionContent: SectionContent? = null,
        val error: String? = null
    )

    val _sectionContentState = MutableStateFlow<SectionContentState?>(viewModelScope, null)
    @NativeCoroutinesState
    val sectionContentState: StateFlow<SectionContentState?> get() = _sectionContentState

    fun makeSectionListApiRequest() {
        val params = SectionListRequestBody(device = "android", api_key = "hindu@9*M", app_version = 78, os_version = 29)
        sectionListUseCase.invoke(scope = viewModelScope.coroutineScope, params, onResult = object : UseCaseResponse<SectionList> {
            override fun onSuccess(type: SectionList) {
                println("Request is successful :: $type")
                _sectionList.update { type }
            }

            override fun onError(apiError: String) {
                println("Request is Failed :: $apiError")
            }

            override fun onLoading(isLoading: Boolean) {
                println("Request is loading :: $isLoading")
            }

        })
    }

    fun makeSectionContentApiRequest(secId: Int, page: Int = 1, lut: Int = 0) {
        val params = SectionContentRequestBody(device = "android", api_key = "hindu@9*M", app_version = 78,
            os_version = 29, id = secId, lut = 0, type = "section", page = page )
        sectionContentUseCase.invoke(scope = viewModelScope.coroutineScope, params, onResult = object : UseCaseResponse<SectionContent> {
            override fun onSuccess(type: SectionContent) {
                println("Request is successful :: $type")
                _sectionContentState.update { SectionContentState(sectionContent = type, isSuccess = true, isLoading = false, error = null) }
            }

            override fun onError(apiError: String) {
                _sectionContentState.update { SectionContentState(isLoading = false, isSuccess = false, error = apiError) }
            }

            override fun onLoading(isLoading: Boolean) {
                _sectionContentState.update { SectionContentState(isLoading = true) }
            }

        })
    }

}