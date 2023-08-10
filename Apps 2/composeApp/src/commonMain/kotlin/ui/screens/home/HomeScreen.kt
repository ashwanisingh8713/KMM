package ui.screens.home

import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.material3.Text
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import domain.model.SectionContent
import kotlinx.coroutines.flow.distinctUntilChanged
import ui.model.SectionTabItem
import ui.screens.home.pages.SectionContentUI_0
import ui.vm.SectionListViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Article
import ext.getScreenModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.screens.detail.DetailScreen
import ui.screens.util.ComposeTag
import ui.screens.util.NoNetworkUI

/**
 * Created by Ashwani Kumar Singh on 31,July,2023.
 */


object HomeScreen: Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        println("$ComposeTag: HomeScreen: Content:")

        val viewModel = getScreenModel<SectionListViewModel>()

        LaunchedEffect(key1 = Unit) {
            viewModel.makeSectionListApiRequest()
        }

        var tabRowItems by remember { mutableStateOf(mutableListOf<SectionTabItem>()) }
        var sectionListLoading by remember { mutableStateOf(true) }
        var sectionListError by remember { mutableStateOf<String?>(null) }

        viewModel.successSectionList.collectAsState().value?.let { it ->
            val secList = it.data.section
            tabRowItems = secList.filter { it.type != "static" }.map {
                SectionTabItem(
                    tabId = it.secId.toString(),
                    secName = it.secName,
                    isSelected = false,
                    secId = it.secId,
                    secType = it.type,
                )
            }.toMutableList()
        }

        viewModel.sectionListLoading.collectAsState().value?.let { it ->
            sectionListLoading = it
        }

        viewModel.sectionListError.collectAsState().value?.let { it ->
            sectionListError = it
        }

        HomeContent(viewModel, tabRowItems = tabRowItems, sectionListLoading = sectionListLoading, sectionListError= sectionListError)
    }


}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(viewModel: SectionListViewModel, tabRowItems: List<SectionTabItem>,
                sectionListLoading: Boolean, sectionListError: String?) {

    println("$ComposeTag: HomeScreen: Content: HomeContent:")

    val navigator = LocalNavigator.currentOrThrow

    val onArticleClick: (article: Article) -> Unit = { article ->
        navigator.push(DetailScreen(article))
    }

    val pagerState = rememberPagerState(initialPage = 0)

    Scaffold(
        bottomBar = {
            BottomAppBar(
                tonalElevation = 10.dp,
            ) {
                HomeBottomBar()

            }
        },
        topBar = {
            println("$ComposeTag: HomeScreen: Content: HomeContent: Scaffold: topBar:")
            Surface(shadowElevation = 3.dp) {
                HomeTopBar()
            }
        },
    ) {
        println("$ComposeTag: HomeScreen: Content: HomeContent: Scaffold: BoxWithConstraints:")
        BoxWithConstraints(
            Modifier.padding(it),
            contentAlignment = Alignment.TopCenter
        ) {
            HomeNavAndTabs(pagerState = pagerState, viewModel = viewModel, tabRowItems = tabRowItems, sectionListLoading = sectionListLoading,
                sectionListError= sectionListError, onArticleClick= onArticleClick)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalResourceApi::class)
@Composable
fun HomeNavAndTabs(pagerState: PagerState, viewModel: SectionListViewModel, tabRowItems: List<SectionTabItem>,
                   sectionListLoading: Boolean, sectionListError: String?,
                   onArticleClick: (article: Article) -> Unit) {

    println("$ComposeTag: HomeScreen: Content: HomeContent: Scaffold: BoxWithConstraints: HomeNavAndTabs:")

    Column(modifier = Modifier.fillMaxSize()) {
        if (sectionListLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Loading Sections ...",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
            }
        } else if (sectionListError != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Image(
                        painter = painterResource("no_network.png"),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = if (sectionListError.contains("Unable to resolve host")) "No Internet Connection" else sectionListError,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )
                }

            }
        } else if (tabRowItems.isNotEmpty()) {
            // Tab Row
            TabLayout(pagerState= pagerState, tabRowItems = tabRowItems)
            // Pager
            Pager(
                pagerState= pagerState,
                tabRowItems = tabRowItems,
                viewModel = viewModel,
                onArticleClick = onArticleClick,
            )
        } else {
            NoNetworkUI("No Sections Found")
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Pager(pagerState: PagerState,
    tabRowItems: List<SectionTabItem>,
    viewModel: SectionListViewModel, onArticleClick: (article: Article) -> Unit,
) {
    println("$ComposeTag: HomeScreen: Content: HomeContent: Scaffold: BoxWithConstraints: HomeNavAndTabs: Pager:")

    var sectionContent by remember { mutableStateOf<SectionContent?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }


    var sectionId by remember { mutableStateOf<Int>(0) }
    var sectionName by remember { mutableStateOf<String>("") }
    var sectionType by remember { mutableStateOf<String>("") }

    println("makeSectionContentApiRequest - SectionContentUI :: secId= $sectionId, secName= $sectionName, type= $sectionType")

    viewModel.sectionContentState.collectAsState().value?.let { it ->
        sectionContent = it
    }

    viewModel.sectionContentLoading.collectAsState().value?.let { it ->
        isLoading = it
    }

    viewModel.sectionContentError.collectAsState().value?.let { it ->
        error = it
        println(error)
    }

    LaunchedEffect(pagerState) {
        snapshotFlow {
            pagerState.currentPage
        }.distinctUntilChanged().collect {
            println("Section Pager - $it")
            println("Section Pager tabRowItems size - ${tabRowItems.size}")
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
        beyondBoundsPageCount = 4,
        userScrollEnabled = false,
        key = {
//            randomUUID()
        },

    ) { index ->
        val pageState = tabRowItems[pagerState.currentPage]
        sectionType = pageState.secType
        sectionId = pageState.secId
        sectionName = pageState.secName

        SectionContentUI_0(
            listState = listState[index],
            sectionContent = sectionContent, isLoading = isLoading, error = error,
            secId = sectionId, secName = sectionName, type = sectionType, onArticleClick = onArticleClick
        )
    }
}

@Composable
fun listStates(): LazyListState {

    return rememberLazyListState()
}