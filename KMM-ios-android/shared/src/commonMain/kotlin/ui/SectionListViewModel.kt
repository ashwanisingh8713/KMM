package ui

import com.rickclephas.kmm.viewmodel.KMMViewModel
import domain.usecase.SectionListUseCase
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import domain.model.SectionList
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Ashwani Kumar Singh on 26,July,2023.
 */
class SectionListViewModel(sectionListUseCase: SectionListUseCase): KMMViewModel() {

    val _sectionList = MutableStateFlow<SectionList?>(viewModelScope, null)

//    @NativeCoroutinesState
    val successState: StateFlow<SectionList?> get() = _sectionList

}