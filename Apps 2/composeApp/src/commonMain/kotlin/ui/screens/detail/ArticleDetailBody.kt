package ui.screens.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberAsyncImagePainter
import domain.model.Article
import taboola.loadTaboolaWidget
import ui.screens.util.htmlDescription

/**
 * Created by Ashwani Kumar Singh on 08,September,2023.
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ArticleDetailBody(article: Article, descriptionFontSize: Int) {
    val lazyListState = rememberLazyListState()
    val firstItemTranslationY by remember {
        derivedStateOf {
            when {
                lazyListState.layoutInfo.visibleItemsInfo.isNotEmpty() && lazyListState.firstVisibleItemIndex == 0 -> lazyListState.firstVisibleItemScrollOffset * .01f
                else -> 0f
            }
        }
    }

    val visibility by remember {
        derivedStateOf {
            when {
                lazyListState.layoutInfo.visibleItemsInfo.isNotEmpty() && lazyListState.firstVisibleItemIndex == 0 -> {
                    val imageSize = lazyListState.layoutInfo.visibleItemsInfo[0].size
                    val scrollOffset = lazyListState.firstVisibleItemScrollOffset
                    scrollOffset / imageSize.toFloat()
                }
                else-> 1f
            }
        }
    }

    LazyColumn(state = lazyListState) {

        item {
            Column(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = rememberAsyncImagePainter(article.im_thumbnail),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
//                        .height(232.dp)
                        .fillMaxHeight()
                        .graphicsLayer {
                            alpha = 1f - visibility
                            translationY = firstItemTranslationY
                        },
                    contentScale = ContentScale.FillBounds,
                )

                // Showing Description of Article in WebView
                htmlDescription(article.de, modifier = Modifier.width(300.dp).height(300.dp))

                // Showing Taboola Widgets
                loadTaboolaWidget(pageUrl = article.al, modifier = Modifier)
            }

        }


    }
}