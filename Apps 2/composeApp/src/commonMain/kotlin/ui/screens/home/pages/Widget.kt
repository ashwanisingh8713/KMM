package ui.screens.home.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.seiko.imageloader.rememberAsyncImagePainter
import domain.mapper.ArticleMapper
import domain.model.Article
import domain.model.Widget
import ui.screens.detail.DetailScreen
import ui.vm.SectionListViewModel

/**
 * Created by Ashwani Kumar Singh on 07,September,2023.
 */


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WidgetHorizontalList(viewModel: SectionListViewModel, isLoading: Boolean, widget: Widget, onWidgetViewAllClick: (article: Article) -> Unit = {}) {

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

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        widget.articles?.size ?: 0
    }
    HorizontalPager(
        pageSpacing = 16.dp,
        beyondBoundsPageCount = 2,
        state = pagerState,
        modifier = Modifier.fillMaxSize().height(300.dp)
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
fun widgetUICard(pagerState: PagerState, article: ArticleMapper, page: Int) {
    val navigator = LocalNavigator.currentOrThrow
    Card(modifier = Modifier.padding(vertical = 1.dp), onClick = {
        navigator.push(DetailScreen(article))
    }) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {



            /*Column(Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 30.dp)) {
                Text(
                    text = article.ti!!,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black,
                )
                Text(
                    text = article.le!!,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Normal),
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black,

                    )
            }*/

            ProductCard(
                product = article,
                navigateToDetailScreen = { navigator.push(DetailScreen(article)) },
            )

        }


    }
}



@Composable
fun ProductCard(
    product: ArticleMapper,
    navigateToDetailScreen: (ArticleMapper) -> Unit,
) {

    Card(
        modifier = Modifier.padding(4.dp).clickable { navigateToDetailScreen(product) },
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {


            val painter = rememberAsyncImagePainter(product.banner!!)
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxWidth().height(150.dp)
                    .padding(4.dp),
            )

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

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text= product.secName!!,
                style = MaterialTheme.typography.titleSmall,
            )


        }
    }

}
