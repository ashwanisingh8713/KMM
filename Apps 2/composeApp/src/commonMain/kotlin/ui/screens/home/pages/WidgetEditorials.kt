package ui.screens.home.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.mapper.ArticleMapper
import domain.model.Article
import domain.model.Widget
import ui.screens.detail.DetailScreen
import ui.sharedui.WidgetTitle
import ui.viewModel.SectionListViewModel

/**
 * Created by Ashwani Kumar Singh on 07,September,2023.
 */


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WidgetEditorials(viewModel: SectionListViewModel, isLoading: Boolean, widget: Widget, onWidgetViewAllClick: (article: Article) -> Unit = {}) {

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

    val navigator = LocalNavigator.currentOrThrow

    if(widget.articles == null) {
        return
    }

    WidgetTitle(widget.secName)

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        widget.articles?.size ?: 0
    }
    HorizontalPager(
        pageSpacing = 16.dp,
        contentPadding = PaddingValues(start = 3.dp, end = 5.dp),
//        beyondBoundsPageCount = 2,
        state = pagerState,
        modifier = Modifier.fillMaxSize().height(100.dp)
    ) { page ->

        Card(modifier = Modifier.fillMaxSize(),
            elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp),
            shape = RoundedCornerShape(10.dp)) {
            val article = widget.articles!![page]
            EditorialCard(
                product = article,
                navigateToDetailScreen = { navigator.push(DetailScreen(article)) },
            )
        }

    }
    Spacer(modifier = Modifier.size(8.dp))
    Divider(color = Color.Black, thickness = 1.dp)

}

@Composable
fun EditorialCard(
    product: ArticleMapper,
    navigateToDetailScreen: (ArticleMapper) -> Unit,
) {

    Box(
        modifier = Modifier.padding(start = 8.dp).clickable { navigateToDetailScreen(product) },

    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                product.ti!!,
                maxLines = 1,
                style = MaterialTheme.typography.titleSmall,
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                product.le!!,
                maxLines = 2,
                style = MaterialTheme.typography.labelSmall,
            )

            Spacer(modifier = Modifier.size(18.dp))

        }
    }

}
