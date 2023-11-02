package ui.screens.home.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberAsyncImagePainter
import domain.mapper.ArticleMapper
import domain.model.Article
import domain.model.Widget
import ui.sharedui.WidgetTitle
import ui.viewModel.SectionListViewModel

/**
 * Created by Ashwani Kumar Singh on 02,November,2023.
 */
@Composable
fun WidgetCartoon(viewModel: SectionListViewModel, isLoading: Boolean, widget: Widget, onWidgetViewAllClick: (article: Article) -> Unit = {}) {

    println("WidgetCartoon - ")
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

    Card(
        modifier = Modifier.fillMaxSize().height(220.dp),
        colors = CardDefaults.cardColors(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column {
            WidgetTitle("Cartoon")
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(start = 8.dp, top = 8.dp)
            ) {
                items(widget.articles!!.size) { result ->
                    CartoonItem(article = widget.articles!![result])
                }

            }
        }
    }
}


@Composable
fun CartoonItem(article: ArticleMapper) {
    println("WidgetCartoon - TopNewsCard")
    Card(
        modifier = Modifier
            .width(220.dp)
            .height(180.dp)
            .clickable {
                // TODO, Click
            },
        colors = CardDefaults.cardColors(),
    ) {
        Box {

            val painter = rememberAsyncImagePainter(article.banner!!)

            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.width(200.dp).height(170.dp)
                    .padding(2.dp),
            )

        }
    }
}

