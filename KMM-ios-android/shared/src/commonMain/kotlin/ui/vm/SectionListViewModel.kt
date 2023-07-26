package ui.vm

import com.rickclephas.kmm.viewmodel.KMMViewModel
import domain.usecase.SectionListUseCase
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import domain.model.SectionList
import domain.usecase.base.UseCaseResponse
import io.ktor.util.logging.Logger
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Ashwani Kumar Singh on 26,July,2023.
 */
class SectionListViewModel(private val sectionListUseCase: SectionListUseCase): KMMViewModel() {

    val _sectionList = MutableStateFlow<SectionList?>(viewModelScope, null)

    @NativeCoroutinesState
    val successState: StateFlow<SectionList?> get() = _sectionList

    fun makeSectionApiRequest() {
        sectionListUseCase.invoke(scope = viewModelScope.coroutineScope, null, onResult = object : UseCaseResponse<SectionList> {
            override fun onSuccess(type: SectionList) {
                println("Request is successful")
            }

            override fun onError(apiError: String) {
                println("Request is Failed :: $apiError")
            }

            override fun onLoading(isLoading: Boolean) {
                println("Request is loading :: $isLoading")
            }

        })
    }

}