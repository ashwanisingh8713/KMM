package ui.screens.home

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.launch
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.derivedStateOf
import androidx.compose.material3.Text
import androidx.compose.material3.Tab
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabPosition

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
import ui.theme.Theme
import ui.vm.SectionListViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Article
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.randomUUID
import ui.screens.detail.DetailScreen

/**
 * Created by Ashwani Kumar Singh on 31,July,2023.
 */


class HomeScreen(private val viewModel: SectionListViewModel, private val tabRowItems: List<SectionTabItem>,
                 private val sectionListLoading: Boolean, private val sectionListError: String?): Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        var tabRowItems by remember { mutableStateOf(listOf<SectionTabItem>()) }
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
            }
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(viewModel: SectionListViewModel, tabRowItems: List<SectionTabItem>,
                sectionListLoading: Boolean, sectionListError: String?) {
    val navigator = LocalNavigator.currentOrThrow

    val onArticleClick: (article: Article) -> Unit = { article ->
        navigator.push(DetailScreen(article))
    }

    val pagerState = rememberPagerState(initialPage = 0) {
        tabRowItems.size
    }

    Scaffold(
        bottomBar = {
            HomeBottomBar()
        },
        topBar = {
            HomeTopBar()
        },
    ) {
        BoxWithConstraints(
            Modifier.padding(it),
            contentAlignment = Alignment.TopCenter
        ) {
            HomeNavAndTabs(pagerState= pagerState, viewModel = viewModel, tabRowItems = tabRowItems, sectionListLoading = sectionListLoading, sectionListError= sectionListError,  onArticleClick= onArticleClick)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalResourceApi::class)
@Composable
fun HomeNavAndTabs(pagerState: PagerState, viewModel: SectionListViewModel, tabRowItems: List<SectionTabItem>,
                   sectionListLoading: Boolean, sectionListError: String?,
                   onArticleClick: (article: Article) -> Unit) {
    println("makeSectionContentApiRequest - HomeContent ::  ::  ::  ::  ::  ::  ::  ::  ::  ::  :: ")

    var indicator by remember { mutableStateOf<(@Composable() (List<TabPosition>) -> Unit)?>(null) }
    Column(modifier = Modifier.fillMaxSize()) {

        if(sectionListLoading) {
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
        } else if(sectionListError != null) {
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
                        text = if(sectionListError.contains("Unable to resolve host")) "No Internet Connection" else sectionListError,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )
                }

            }
        }
        else if (tabRowItems.isNotEmpty()) {

            val currentTabIndex by remember { derivedStateOf { pagerState.currentPage } }

            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.fillMaxWidth(),
                edgePadding = 0.dp,
                indicator = { tabPositions ->
                    CustomIndicator(tabPositions = tabPositions, pagerState = pagerState)
                },

                ) {
                tabRowItems.forEachIndexed { index, tabItem ->
                    val selected = currentTabIndex == index
                    val coroutineScope = rememberCoroutineScope()
                    Tab(
                        /*modifier = if (selected) Modifier
                            .clip(RoundedCornerShape(50))
                            .background(
                                Color.White
                            )
                        else Modifier
                            .clip(RoundedCornerShape(50))
                            .background(
                                Color(
                                    0xff1E76DA
                                )
                            ),*/
                        selected = selected,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = tabItem.secName.uppercase(),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                color = if (selected) Theme.colors.primary else Theme.colors.onSurfaceVariant,
                            )
                        }
                    )
                }
            }
            // Pager
            Pager(pagerState = pagerState, tabRowItems = tabRowItems, viewModel = viewModel, onArticleClick = onArticleClick)
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Image(
                        painter = painterResource("no_network.png"),
                        contentDescription = null,
                        modifier = Modifier
                            .height(50.dp)

                    )
                    Text(
                        text = "No Sections Found",
                        style = MaterialTheme.typography.displayMedium,
                        textAlign = TextAlign.Center
                    )
                }

            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Pager(
    pagerState: PagerState,
    tabRowItems: List<SectionTabItem>,
    viewModel: SectionListViewModel, onArticleClick: (article: Article) -> Unit
) {
    println("makeSectionContentApiRequest - HorizontalPager ::  ::  ::  ::  ::  ::  ::  ::  ::  ::  :: ")

    var sectionContent by remember { mutableStateOf<SectionContent?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    var type by remember { mutableStateOf<String>("") }
    var secId by remember { mutableStateOf<Int>(0) }
    var secName by remember { mutableStateOf<String>("") }

    println("makeSectionContentApiRequest - SectionContentUI :: secId= $secId, secName= $secName, type= $type")

    LaunchedEffect(pagerState) {
        snapshotFlow {
            pagerState.currentPage
        }.distinctUntilChanged().collect {
            viewModel.makeSectionContentApiRequest(
                secId = secId,
                secName = secName,
                type = type,
                page = 1
            )
        }
    }

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


    HorizontalPager(
        state = pagerState,
        beyondBoundsPageCount = 0,
        userScrollEnabled = false,
        key = {
            randomUUID()
        }
    ) { index ->
        val pageState = tabRowItems[pagerState.currentPage]
        type = pageState.secType
        secId = pageState.secId
        secName = pageState.secName

        SectionContentUI_0(
            sectionContent = sectionContent, isLoading = isLoading, error = error,
            secId = secId, secName = secName, type = type, onArticleClick = onArticleClick
        )


    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CustomIndicator(tabPositions: List<TabPosition>, pagerState: PagerState) {
    val transition = updateTransition(pagerState.currentPage, label = "")
    val indicatorStart by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 50f)
            } else {
                spring(dampingRatio = 1f, stiffness = 100f)
            }
        }, label = ""
    ) {
        tabPositions[it].left
    }

    val indicatorEnd by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 100f)
            } else {
                spring(dampingRatio = 1f, stiffness = 50f)
            }
        }, label = ""
    ) {
        tabPositions[it].right
    }

    Box(
        Modifier
            .offset(x = indicatorStart)
            .wrapContentSize(align = Alignment.BottomStart)
            .width(indicatorEnd - indicatorStart)
            .fillMaxSize()
            .border(BorderStroke(2.dp, Theme.colors.primary), RoundedCornerShape(30))
            .padding(25.dp)
    )
}





