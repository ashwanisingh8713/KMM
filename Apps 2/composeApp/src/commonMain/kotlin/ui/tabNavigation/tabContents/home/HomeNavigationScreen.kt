package ui.tabNavigation.tabContents.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Article
import ext.getScreenModel
import ui.model.SectionTabItem
import ui.screens.detail.DetailScreen
import ui.screens.home.HomeBottomBar
import ui.screens.home.HomeContent
import ui.screens.home.HomeNavAndTabs
import ui.screens.home.HomeTopBar
import ui.screens.util.ComposeTag
import ui.tabNavigation.tabs.BasicNavigationScreen
import ui.vm.SectionListViewModel

/**
 * Created by Ashwani Kumar Singh on 11,August,2023.
 */
data class HomeNavigationScreen(val index: Int,
                                val wrapContent: Boolean = false): Screen {

    @Composable
    override fun Content() {
        LifecycleEffect(
            onStarted = {
//                Log.d("Navigator", "Start screen #$index")
            },
            onDisposed = {
//                Log.d("Navigator", "Dispose screen #$index")
            },
        )

        val navigator = LocalNavigator.currentOrThrow

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

        HomeTabPagerUI(viewModel, tabRowItems = tabRowItems, sectionListLoading = sectionListLoading, sectionListError= sectionListError)
    }

}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeTabPagerUI(viewModel: SectionListViewModel, tabRowItems: List<SectionTabItem>,
                sectionListLoading: Boolean, sectionListError: String?) {

    println("$ComposeTag: HomeScreen: Content: HomeContent:")

    val navigator = LocalNavigator.currentOrThrow

    val onArticleClick: (article: Article) -> Unit = { article ->
        navigator.push(DetailScreen(article))
    }

    val pagerState = rememberPagerState(initialPage = 0)

    Scaffold(
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
