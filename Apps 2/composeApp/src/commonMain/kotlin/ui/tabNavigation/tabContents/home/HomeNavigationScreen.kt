package ui.tabNavigation.tabContents.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
import domain.model.Article
import ext.getScreenModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.model.SectionTabItem
import ui.screens.detail.DetailScreen
import ui.screens.home.TabLayout
import ui.screens.home.pages.SectionContentUI_0
import ui.screens.util.ComposeTag
import ui.screens.util.NoNetworkUI
import ui.vm.SectionListViewModel

/**
 * Created by Ashwani Kumar Singh on 11,August,2023.
 */

@OptIn(ExperimentalFoundationApi::class)
class HomeNavigationScreen constructor(
    val wrapContent: Boolean = false,
) : Screen {
    override val key = uniqueScreenKey

    @OptIn(ExperimentalFoundationApi::class, ExperimentalResourceApi::class)
    @Composable
    override fun Content() {

        val viewModel = getScreenModel<SectionListViewModel>()

        var tabRowItems by remember { mutableStateOf(mutableListOf<SectionTabItem>()) }
        var sectionListLoading by remember { mutableStateOf(true) }
        var sectionListError by remember { mutableStateOf<String?>(null) }

        var sectionId by remember { mutableStateOf<Int>(0) }
        var sectionName by remember { mutableStateOf<String>("") }
        var sectionType by remember { mutableStateOf<String>("") }

        val sectionContent by viewModel.sectionContentState.collectAsState()
        val isLoading by viewModel.sectionContentLoading.collectAsState()
        val error by viewModel.sectionContentError.collectAsState()

        var selectedPage by remember { mutableStateOf<Int>(0) }

        val pagerState = rememberPagerState(initialPage = selectedPage)

        // To handle list state for each page, it should have same size as pagerState.pageCount
        var listState: List<LazyListState>? = null



        LaunchedEffect(true) {
            println("$ComposeTag: HomeNavigationScreen: TabLayout: LaunchedEffect:")
            viewModel.makeSectionListApiRequest()
        }

        viewModel.successSectionList.collectAsState().value?.let { it ->
            val secList = it.data?.section
            tabRowItems =
                if (secList == null) mutableListOf<SectionTabItem>() else secList?.filter { it.type != "static" }
                    ?.map {
                        SectionTabItem(
                            tabId = it.secId.toString(),
                            secName = it.secName,
                            isSelected = false,
                            secId = it.secId,
                            secType = it.type,
                        )
                    }!!.toMutableList()

            // Initialize listState
            listState = List(tabRowItems.size) { rememberLazyListState() }
        }
            viewModel.sectionListLoading.collectAsState().value?.let { it ->
                sectionListLoading = it
            }

            viewModel.sectionListError.collectAsState().value?.let { it ->
                sectionListError = it
            }


            Column(modifier = Modifier.fillMaxSize()) {
                if (sectionListLoading) {
                    println("$ComposeTag: HomeNavigationScreen: HomeNavigationAndTabs: sectionListLoading:")
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
                } else if (sectionListError != null && tabRowItems.isEmpty()) {
                    println("$ComposeTag: HomeNavigationScreen: HomeNavigationAndTabs: sectionListError:")
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
                                text = if (sectionListError!!.contains("Unable to resolve host")) "No Internet Connection" else sectionListError!!,
                                style = MaterialTheme.typography.headlineMedium,
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                } else if (tabRowItems.isNotEmpty()) {

                    /////////////////////////////////////////////////////////////////
                    // TODO, Top Tab & Pager
                    /////////////////////////////////////////////////////////////////


                    val navigator = LocalNavigator.currentOrThrow

                    val onArticleClick: (article: Article) -> Unit = { article ->
                        navigator.push(DetailScreen(article))
                    }


                    Column(modifier = Modifier.fillMaxSize()) {

                        LaunchedEffect(pagerState.currentPage) {
                            if (sectionContent == null || sectionContent?.data?.sid != sectionId) {
                                viewModel.makeSectionContentApiRequest(
                                    secId = sectionId,
                                    secName = sectionName,
                                    type = sectionType,
                                    page = 1
                                )
                            }
                        }

                        // Tab Row
                        TabLayout(pagerState = pagerState, tabRowItems = tabRowItems)

                        // Pager
                        HorizontalPager(
                            pageCount = tabRowItems.size,
                            state = pagerState,
                            beyondBoundsPageCount = 0,
                            userScrollEnabled = false,
//                key = {
//                    uniqueScreenKey
//                },
                        )
                        { index ->
                            val pageState = tabRowItems[pagerState.currentPage]
                            sectionType = pageState.secType
                            sectionId = pageState.secId
                            sectionName = pageState.secName

                            selectedPage = pagerState.currentPage

                            SectionContentUI_0(
                                listState = listState!![index],
                                sectionContent = sectionContent,
                                isLoading = isLoading,
                                error = error,
                                secId = pageState.secId,
                                secName = pageState.secName,
                                type = pageState.secType,
                                onArticleClick = onArticleClick
                            )
                        }
                    }


                } else {
                    println("$ComposeTag: HomeNavigationScreen: HomeNavigationAndTabs: NoNetworkUI")
                    NoNetworkUI("No Sections Found")
                }
            }



        }


}

