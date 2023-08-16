package ui.screens.home

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import colors.LocalAppColors
import colors.ProvideAppColors
import kotlinx.coroutines.launch
import theme.AppTheme
import theme.Theme
import ui.model.SectionTabItem
import ui.screens.util.ComposeTag

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabLayout(pagerState: PagerState, tabRowItems: List<SectionTabItem>) {
    println("$ComposeTag: HomeNavigationScreen: TabLayout: pagerState.currentPage: ${pagerState.currentPage}")
//    val currentTabIndex by remember { derivedStateOf { pagerState.currentPage } }
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = Modifier.fillMaxWidth(),
        edgePadding = 0.dp,
        indicator = { tabPositions ->
            //CustomIndicator(tabPositions = tabPositions, pagerState = pagerState)
        },

        ) {
        tabRowItems.forEachIndexed { index, tabItem ->
//            val selected = currentTabIndex == index
            val selected = pagerState.currentPage == index
            val coroutineScope = rememberCoroutineScope()
            Tab(
                /*modifier = if (selected) Modifier
                    .clip(RoundedCornerShape(50))
                    .background(
                        Color(0xff1E76DA)
                    )
                else Modifier
                    .background(
                        Color.White
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
                        color = if (selected) Theme.colors.primary else Theme.colors.onPrimary,
                    )
                }
            )
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
//            .border(BorderStroke(2.dp, Theme.colors.primary), RoundedCornerShape(30))
            .padding(25.dp)
    )
}