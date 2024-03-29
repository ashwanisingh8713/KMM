package ui.viewModel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.SectionListUseCase
import data.datasource.SectionContentRequestBody
import data.datasource.SectionListRequestBody
import data.model.SectionContentListData
import domain.mapper.ArticleMapper
import domain.model.SectionList
import domain.model.Widget
import domain.usecase.SectionContentUseCase
import domain.usecase.base.UseCaseResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import ui.sharedui.ComposeTag
import ui.screens.util.ViewType

/**
 * Created by Ashwani Kumar Singh on 26,July,2023.
 */
class SectionListViewModel(private val sectionListUseCase: SectionListUseCase,
                           val sectionContentUseCase: SectionContentUseCase):
    ScreenModel, KoinComponent {


    private val _sectionList = MutableStateFlow<SectionList>(SectionList())
    val successSectionList: StateFlow<SectionList?> get() = _sectionList

    private val _sectionListLoading = MutableStateFlow<Boolean>( true)
    val sectionListLoading: StateFlow<Boolean> get() = _sectionListLoading

    private val _sectionListError = MutableStateFlow<String?>( null)
    val sectionListError: StateFlow<String?> get() = _sectionListError

    /**
     * This method is used to make api request for sections top Tabbar list
     */
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

    // It holds the contents of single section
    private val _sectionContentState = MutableStateFlow<MutableList<SectionContentListData?>?>( null)
    val sectionContentState: StateFlow<MutableList<SectionContentListData?>?> get() = _sectionContentState

    private val _sectionContentLoading = MutableStateFlow<Boolean>( true)
    val sectionContentLoading: StateFlow<Boolean> get() = _sectionContentLoading

    private val _sectionContentError = MutableStateFlow<String?>( null)
    val sectionContentError: StateFlow<String?> get() = _sectionContentError

    /**
     * This method is used to make api request for section content list for a particular section
     */
    fun makeSectionContentApiRequest(secId: Int, secName: String, type:String, page: Int = 1, lut: Int = 0, widgets: List<Widget>) {
        println("$ComposeTag: LaunchedEffect -> secId = $secId, secName = $secName, secType = $type")
        val params = SectionContentRequestBody(device = "android", api_key = "hindu@9*M", app_version = 78,
            os_version = 29, id = secId, lut = 0, type = type, page = page )
        sectionContentUseCase.invoke(scope = coroutineScope, params, onResult = object : UseCaseResponse<List<ArticleMapper>> {
            override fun onSuccess(content: List<ArticleMapper>) {
                println("makeSectionContentApiRequest - ViewModel :: secId= $secId, secName= $secName, type= $type")
                val sectionContentList = mutableListOf<SectionContentListData?>()

                content.forEachIndexed { index, it ->
                    if(index%3 == 0 && index != 0) {
                        val bannerAdsItem = SectionContentListData(secId = index, viewType = ViewType.VIEW_TYPE_BANNER_ADS)
                        sectionContentList.add(bannerAdsItem)
                    }
                    val articleItem = SectionContentListData(secId = secId, viewType = ViewType.VIEW_TYPE_ARTICLE, article = it, widget = null)
                    sectionContentList.add(articleItem)
                }
                var widgetIndexCount = 2
                widgets.forEach {
                    val viewType = widgetViewType(it.secId)
                    val sectionContentListData = SectionContentListData(secId = it.secId, viewType = viewType, article = null, widget = it)
                    sectionContentList.add(widgetIndexCount, sectionContentListData)
                    widgetIndexCount += 2+1
                }

                _sectionContentState.update { sectionContentList }

            }

            override fun onError(apiError: String) {
                _sectionContentState.update { null }
                _sectionContentError.value = apiError
            }

            override fun onLoading(isLoading: Boolean) {
                _sectionContentLoading.value = isLoading
            }
        })
    }





    private val _widgetLoading = MutableStateFlow<Boolean>( true)
    val widgetLoading: StateFlow<Boolean> get() = _widgetLoading

    private val _widgetError = MutableStateFlow<String?>( null)
    val widgetError: StateFlow<String?> get() = _widgetError

    fun widgetViewType(sid:Int) : String {
        return when(sid) {
            69 -> ViewType.VIEW_TYPE_WIDGET_CARTOON
            5 -> ViewType.VIEW_TYPE_WIDGET_EDITORIAL
            else -> ViewType.VIEW_TYPE_WIDGET
        }
    }

    /**
     * This method is used to make api request for widget article's content list
     */
    fun makeWidgetContentApiRequest(secId: Int, secName: String, type:String, page: Int = 1, lut: Int = 0) {
        println("WidgetCartoon: LaunchedEffect -> secId = $secId, secName = $secName, secType = $type")
        val params = SectionContentRequestBody(device = "android", api_key = "hindu@9*M", app_version = 78,
            os_version = 29, id = secId, lut = 0, type = type, page = page )
        sectionContentUseCase.invoke(scope = coroutineScope, params, onResult = object : UseCaseResponse<List<ArticleMapper>> {
            override fun onSuccess(widgetContent: List<ArticleMapper>) {
                val sid = widgetContent[0].secId
                val sectionContentList = _sectionContentState.value!!

                val viewType = widgetViewType(sid!!.toInt())

                val index = sectionContentList.indexOf(SectionContentListData(secId = secId, viewType = viewType, article = null, widget = null))
                if(index != -1) {
                    sectionContentList[index]?.widget?.articles = widgetContent.toMutableList()
                    _sectionContentState.update { sectionContentList }
                }

            }

            override fun onError(apiError: String) {
                _widgetError.value = apiError
            }

            override fun onLoading(isLoading: Boolean) {
                _widgetLoading.value = isLoading
            }
        })
    }


}