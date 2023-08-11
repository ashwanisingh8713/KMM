package ui.tabNavigation.tabContents.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Article
import domain.model.SectionContent
import ext.getScreenModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.model.SectionTabItem
import ui.screens.detail.DetailScreen
import ui.screens.home.HomeBottomBar
import ui.screens.home.HomeContent
import ui.screens.home.HomeNavAndTabs
import ui.screens.home.HomeTopBar
import ui.screens.home.TabLayout
import ui.screens.home.pages.SectionContentUI_0
import ui.screens.util.ComposeTag
import ui.screens.util.NoNetworkUI
import ui.tabNavigation.tabs.BasicNavigationScreen
import ui.vm.SectionListViewModel

/**
 * Created by Ashwani Kumar Singh on 11,August,2023.
 */
class HomeNavigationScreen @OptIn(ExperimentalFoundationApi::class) constructor(val index: Int,
                                                                                     val wrapContent: Boolean = false,
                                                                                     var tabRowItems:List<SectionTabItem>,
                                                                                     var pagerState: PagerState): Screen {
    override val key = uniqueScreenKey

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {

        println("$ComposeTag ---------------  WrapContent: $wrapContent and index $index -----------------")
        println("$ComposeTag: HomeNavigationScreen: Content: HomeContent:")

        LifecycleEffect(
            onStarted = {
//                Log.d("Navigator", "Start screen #$index")
            },
            onDisposed = {
//                Log.d("Navigator", "Dispose screen #$index")
            },
        )

        val viewModel = getScreenModel<SectionListViewModel>()

        println("$ComposeTag: HomeNavigationScreen: Content: HomeTabPagerUI:")

        val navigator = LocalNavigator.currentOrThrow

        val onArticleClick: (article: Article) -> Unit = { article ->
            navigator.push(DetailScreen(article))
        }




        Column(modifier = Modifier.fillMaxSize()) {
            // Tab Row
            TabLayout(pagerState= pagerState, tabRowItems = tabRowItems)
            // Pager
            HomeNavigationPager(
                pagerState= pagerState,
                tabRowItems = tabRowItems,
                viewModel = viewModel,
                onArticleClick = onArticleClick
            )
        }
    }

}



@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeNavigationPager(pagerState: PagerState,
                  tabRowItems: List<SectionTabItem>,
                  viewModel: SectionListViewModel, onArticleClick: (article: Article)-> Unit,
) {
    println("$ComposeTag: HomeNavigationScreen: HomeNavigationPager:")

    var sectionId by remember { mutableStateOf<Int>(0) }
    var sectionName by remember { mutableStateOf<String>("") }
    var sectionType by remember { mutableStateOf<String>("") }

    val sectionContent by viewModel.sectionContentState.collectAsState()
    val isLoading by viewModel.sectionContentLoading.collectAsState()
    val error by viewModel.sectionContentError.collectAsState()

    LaunchedEffect(pagerState) {
        snapshotFlow {
            pagerState.currentPage
        }.distinctUntilChanged().collect {
//                println("$ComposeTag: HomeNavigationScreen: HomeNavigationPager: LaunchedEffect -> secId = $sectionId, secName = $sectionName, secType = $sectionType")
//            println("Section Pager - $it")
//            println("Section Pager tabRowItems size - ${tabRowItems.size}")
            viewModel.makeSectionContentApiRequest(
                secId = sectionId,
                secName = sectionName,
                type = sectionType,
                page = 1
            )
        }
    }

    // To handle list state for each page, it should have same size as pagerState.pageCount
    val listState: List<LazyListState> = List(tabRowItems.size) { rememberLazyListState() }

    HorizontalPager(pageCount = tabRowItems.size) {

    }

    HorizontalPager(
        pageCount = 10,
        state = pagerState,
        beyondBoundsPageCount = 0,
        userScrollEnabled = false,
//        key = {
////            randomUUID()
//        },

    ) { index ->
        val pageState = tabRowItems[pagerState.currentPage]
        sectionType = pageState.secType
        sectionId = pageState.secId
        sectionName = pageState.secName


        SectionContentUI_0(
            listState = listState[index],
            sectionContent = sectionContent, isLoading = isLoading, error = error,
            secId = pageState.secId, secName = pageState.secName, type = pageState.secType, onArticleClick = onArticleClick
        )
    }
}
