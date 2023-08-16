package ui.tabNavigation.tabContents.home

import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.transitions.SlideTransition
import ext.getScreenModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.model.SectionTabItem
import ui.screens.util.ComposeTag
import ui.screens.util.NoNetworkUI
import ui.vm.SectionListViewModel

@OptIn(ExperimentalAnimationApi::class, ExperimentalResourceApi::class,
)
@Composable
fun Tab.HomeTabContent() {
    val tabTitle = options.title

//    LifecycleEffect(
//        onStarted = { Log.d("Navigator", "Start tab $tabTitle") },
//        onDisposed = { Log.d("Navigator", "Dispose tab $tabTitle") },
//    )


    val viewModel = getScreenModel<SectionListViewModel>()

    var tabRowItems by remember { mutableStateOf(mutableListOf<SectionTabItem>()) }
    var sectionListLoading by remember { mutableStateOf(true) }
    var sectionListError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(true) {
        println("$ComposeTag: HomeNavigationScreen: TabLayout: LaunchedEffect:")
        viewModel.makeSectionListApiRequest()
    }



    viewModel.successSectionList.collectAsState().value?.let { it ->
        val secList = it.data?.section
        tabRowItems = if(secList == null) mutableListOf<SectionTabItem>() else secList?.filter { it.type != "static" }?.map {
            SectionTabItem(
                tabId = it.secId.toString(),
                secName = it.secName,
                isSelected = false,
                secId = it.secId,
                secType = it.type,
            )
        }!!.toMutableList()
        /*tabRowItems = secList?.filter { it.type != "static" }?.map {
            SectionTabItem(
                tabId = it.secId.toString(),
                secName = it.secName,
                isSelected = false,
                secId = it.secId,
                secType = it.type,
            )
        }!!.toMutableList()*/
    }

    viewModel.sectionListLoading.collectAsState().value?.let { it ->
        sectionListLoading = it
    }

    viewModel.sectionListError.collectAsState().value?.let { it ->
        sectionListError = it
    }

    /*Navigator(
        HomeNavigationScreen(wrapContent = false,
            tabRowItems= tabRowItems)
    ) { navigator ->
        SlideTransition(navigator) { screen ->

            screen.Content()
        }
    }*/



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
        } else if (sectionListError != null) {
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
            var wrap by remember { mutableStateOf(false) }
            var index by remember { mutableStateOf(0) }

            var lamdaValue: (Int) -> Unit = { index ->
                println("$ComposeTag: HomeNavigationScreen: HomeNavigationAndTabs: lamdaValue: $index")

            }

            println("$ComposeTag: HomeNavigationScreen: HomeNavigationAndTabs: tabRowItems: ${tabRowItems.size} count")

            Navigator(
                HomeNavigationScreen(wrapContent = wrap,
                tabRowItems= tabRowItems)
            ) { navigator ->
                SlideTransition(navigator) { screen ->

                    screen.Content()
                }
            }
        } else {
            println("$ComposeTag: HomeNavigationScreen: HomeNavigationAndTabs: NoNetworkUI")
            NoNetworkUI("No Sections Found")
        }
    }

    //////////////////////////////////////////////////////////


}



