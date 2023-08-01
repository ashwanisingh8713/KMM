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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabPosition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import ui.model.SectionTabItem
import ui.theme.Theme
import ui.vm.SectionListViewModel

/**
 * Created by Ashwani Kumar Singh on 31,July,2023.
 */

@Composable
fun HomeScreen(viewModel: SectionListViewModel) {
    HomeContent(viewModel)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(viewModel: SectionListViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        LaunchedEffect(true) {
             viewModel.makeSectionListApiRequest()
        }

        var tabRowItems by remember { mutableStateOf(listOf<SectionTabItem>()) }

        viewModel.successSectionList.collectAsState().value?.let { it ->
            val secList = it.data.section
            tabRowItems = secList.map {
                SectionTabItem(tabId = it.secId.toString(),
                    secName = it.secName,
                    isSelected = false,
                    secId = it.secId.toString(),
                    secType = it.type,
                    screen = { SectionContentUI(secId = it.secId, viewModel = viewModel) })
            }
        }

        if(tabRowItems.isNotEmpty()) {
            val pagerState = rememberPagerState(initialPage = 0,) {
                tabRowItems.size
            }
            val currentTabIndex by remember { derivedStateOf { pagerState.currentPage } }

            // Working on Divider
            // SECTIon api, listing screen
            ScrollableTabRow(
//                containerColor = Color(0xff1E76DA),
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.fillMaxWidth(),
                edgePadding = 0.dp,
                indicator = { tabPositions ->
                    CustomIndicator(tabPositions = tabPositions, pagerState = pagerState)
                },
//                divider = {VerticalDivider()},

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


            HorizontalPager(
                state = pagerState,
            ) {
                tabRowItems[pagerState.currentPage].screen()
            }
        }
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





