package ui.screens.home.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults.color
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.seiko.imageloader.rememberAsyncImagePainter
import data.model.SectionContentListData
import domain.model.Article
import domain.model.Widget
import ui.screens.detail.DetailScreen
import ui.screens.util.ViewType
import ui.vm.SectionListViewModel

/**
 * Created by Ashwani Kumar Singh on 31,July,2023.
 */

@Composable
fun SectionContentListUI(
    viewModel: SectionListViewModel,
    listState: LazyListState,
    sectionContent: MutableList<SectionContentListData?>?,
    onArticleClick: (article: Article) -> Unit,
    isLoading: Boolean,
    error: String?,
    secId: Int,
    secName: String,
    type: String,
    widget: List<Widget>
) {
    if (isLoading) { // Loading Block

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )
        }
    } else if (error != null && sectionContent == null) { // Error Block
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = error,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Left,
            )
        }
    } else if (sectionContent != null) { // Data Block
        LazyColumn(
            state = listState,
            modifier = Modifier,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {
            itemsIndexed(items = sectionContent, key = { _, item ->
                item?.article?.aid ?: item?.widget?.secId ?: 0
            }) { index, item ->

                when(item?.viewType) {
                    ViewType.VIEW_TYPE_ARTICLE -> {
                        PostCard_New(
                            isLoading = isLoading,
                            article = item!!.article!!,
                            onArticleClick = onArticleClick
                        )
                        Divider(color = Color.Black, thickness = 1.dp)
                    }
                    ViewType.VIEW_TYPE_WIDGET -> {
                        WidgetCard(
                            viewModel = viewModel,
                            isLoading = isLoading,
                            widget = item!!.widget!!
                        )

                    } else -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No Data",
                                style = MaterialTheme.typography.displayMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }


            }

        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No Data",
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WidgetCard(viewModel: SectionListViewModel, isLoading: Boolean, widget: Widget, onWidgetViewAllClick: (article: Article) -> Unit = {}) {

    LaunchedEffect(true) {
        if(widget.articles == null) {
            viewModel.makeWidgetContentApiRequest(
                secId = widget.secId,
                secName = widget.secName,
                type = widget.type,
                page = 1,
            )
        }
    }

    if(widget.articles == null) {
        return
    }
    Text(
        text = "Widget : ${widget.secName} : ${widget.articles?.size}",
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
        textAlign = TextAlign.Left,
        modifier = Modifier.padding(top = 8.dp)
    )

        val pagerState = rememberPagerState(initialPage = 0)
        HorizontalPager(
            pageCount = widget.articles?.size ?: 0,
            pageSpacing = 16.dp,
            beyondBoundsPageCount = 2,
            state = pagerState,
            modifier = Modifier.fillMaxSize().height(200.dp)
        ) { page ->
            Box(modifier = Modifier.fillMaxSize()) {
                // Contains Image and Text composables
                widgetUICard(
                    pagerState = pagerState,
                    page = page,
                    article = widget.articles!![page]
                )
            }

        }
    Divider(color = Color.Black, thickness = 1.dp)

}

/**
 * It represents the UI of a single widget
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun widgetUICard(pagerState: PagerState, article: Article, page: Int) {
    val navigator = LocalNavigator.currentOrThrow
    Card(modifier = Modifier.padding(vertical = 1.dp), onClick = {
        navigator.push(DetailScreen(article))
    }) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {

                Image(
                    painter = rememberAsyncImagePainter(if(article.me.isNotEmpty()) article.me[0].im else article.im_thumbnail),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                )

                Column(Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 30.dp)) {
                    Text(
                        text = article.ti,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Black,
                    )
                    Text(
                        text = article.le,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Normal),
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Black,

                    )
                }

            }


        }
}



