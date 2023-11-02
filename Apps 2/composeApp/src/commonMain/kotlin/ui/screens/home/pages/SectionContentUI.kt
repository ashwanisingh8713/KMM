package ui.screens.home.pages

import ads.AdmobBanner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.navigator.Navigator
import domain.mapper.ArticleMapper
import domain.model.Widget
import ui.screens.detail.DetailScreen
import ui.screens.util.ViewType
import ui.sharedui.NoNetworkUI
import ui.viewModel.SectionListViewModel

/**
 * Created by Ashwani Kumar Singh on 31,July,2023.
 */

@Composable
fun SectionContentListUI(
    viewModel: SectionListViewModel,
    listState: LazyListState,
    secId: Int,
    secName: String,
    type: String,
    widget: List<Widget>,
    navigator :Navigator
) {

    // https://stackoverflow.com/questions/70589639/lazycolumn-to-not-recompose-some-items

    val sectionContent by viewModel.sectionContentState.collectAsState()
    val isLoading by viewModel.sectionContentLoading.collectAsState()
    val error by viewModel.sectionContentError.collectAsState()

    val onArticleClick: (article: ArticleMapper) -> Unit = { article ->
                        val allArticles = sectionContent?.filter { it?.viewType == ViewType.VIEW_TYPE_ARTICLE }?.map { it?.article!! }
                        allArticles?.let { navigator.push(DetailScreen(article = article, allArticles = it)) }
    }

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
            NoNetworkUI(error!!)
        }
    } else if (sectionContent != null) { // Data Block
        LazyColumn(
            state = listState,
            modifier = Modifier,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {
            itemsIndexed(items = sectionContent!!, /*key = { _, item ->
                item?.article?.aid ?: item?.widget?.secId ?: 0
            }*/) { index, item ->

                println("WidgetCartoon - viewType = ${item?.viewType}")

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
                        WidgetHorizontalList(
                            viewModel = viewModel,
                            isLoading = isLoading,
                            widget = item!!.widget!!
                        )
                    }
                    ViewType.VIEW_TYPE_WIDGET_CARTOON -> {
                        WidgetCartoon(
                            viewModel = viewModel,
                            isLoading = isLoading,
                            widget = item!!.widget!!
                        )
                    }
                    ViewType.VIEW_TYPE_BANNER_ADS -> {
                        AdmobBanner(modifier= Modifier.fillMaxWidth().height(300.dp))
                    }else -> {
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



