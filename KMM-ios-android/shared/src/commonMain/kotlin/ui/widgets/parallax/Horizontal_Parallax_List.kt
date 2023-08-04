package ui.widgets.parallax

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

/**
 * Created by Ashwani Kumar Singh on 04,August,2023.
 */

//https://betterprogramming.pub/tldr-parallax-scrolling-with-jetpack-compose-ea2fe092a718
// Ref Link = https://stackoverflow.com/questions/72528090/offset-a-wide-image-for-horizontal-parallax-effect-in-android-compose

@Composable
private fun ListBg(
    firstVisibleIndex: Int,
    totalVisibleItems: Int,
    firstVisibleItemOffset: Int,
    itemsCount: Int,
    itemWidth: Dp,
    maxWidth: Dp
) {
    val density = LocalDensity.current
    val firstItemOffsetDp = with(density) { firstVisibleItemOffset.toDp() }
    val hasNoScroll = itemsCount <= totalVisibleItems
    val totalWidth = if (hasNoScroll) maxWidth else maxWidth * 2
    val scrollableBgWidth = if (hasNoScroll) maxWidth else totalWidth - maxWidth
    val scrollStep = scrollableBgWidth / itemsCount
    val firstVisibleScrollPercentage = firstItemOffsetDp.value / itemWidth.value
    val xOffset =
        if (hasNoScroll) 0.dp else -(scrollStep * firstVisibleIndex) - (scrollStep * firstVisibleScrollPercentage)
    Box(
        Modifier
            .wrapContentWidth(unbounded = true, align = Alignment.Start)
            .offset { IntOffset(x = xOffset.roundToPx(), y = 0) }
    ) {
        val painterResource: Resource<Painter> = asyncPainterResource("https://placekitten.com/2000/400")
        KamelImage(
            alignment = Alignment.TopCenter,
            modifier = Modifier
                .height(232.dp)
                .width(totalWidth),
            resource = painterResource,
            contentDescription = "Profile",
            onLoading = { progress -> CircularProgressIndicator(progress) },
            onFailure = { exception ->

            }
        )

    }
}

@Composable
fun ListWithParallaxImageScreen() {
    val lazyListState = rememberLazyListState()
    val firstVisibleIndex by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex
        }
    }
    val totalVisibleItems by remember {
        derivedStateOf {
            lazyListState.layoutInfo.visibleItemsInfo.size
        }
    }
    val firstVisibleItemOffset by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemScrollOffset
        }
    }
    val itemsCount = 10
    val itemWidth = 300.dp
    val itemPadding = 16.dp
    BoxWithConstraints(Modifier.fillMaxSize()) {
        ListBg(
            firstVisibleIndex,
            totalVisibleItems,
            firstVisibleItemOffset,
            itemsCount,
            itemWidth + (itemPadding * 2),
            maxWidth
        )
        LazyRow(state = lazyListState, modifier = Modifier.fillMaxSize()) {
            items(itemsCount) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.LightGray.copy(alpha = .5f),
                    ),
                    modifier = Modifier
                        .padding(itemPadding)
                        .width(itemWidth)
                        .height(200.dp)
                ) {
                    Text(
                        text = "Item $it",
                        Modifier
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}