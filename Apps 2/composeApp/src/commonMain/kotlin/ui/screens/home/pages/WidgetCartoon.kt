package ui.screens.home.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seiko.imageloader.rememberAsyncImagePainter
import domain.mapper.ArticleMapper
import domain.model.Article
import domain.model.Widget
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
            LatestNewsText()
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

@Composable
fun LatestNewsText() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 6.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(0.75f),
            text = "Cartoon",
            style = TextStyle(
                fontSize = 22.sp,
                lineHeight = 20.8.sp,
                fontWeight = FontWeight(700),

            )
        )
        Text(
            text = "See All",
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight(600),
            )
        )
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Sharp.KeyboardArrowRight,
                contentDescription = "forward",
                tint = Color(0xFF0080FF),
            )
        }


    }
}