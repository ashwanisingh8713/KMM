package ui.sharedui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

/**
 * Created by Ashwani Kumar Singh on 20,September,2023.
 */

@Composable
fun <T> LoadingView(
    modifier: Modifier = Modifier,
    loading: Boolean,
    content: @Composable () -> T
) {
    Box(modifier = modifier) {
        content()
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun DetailBanner(article: ArticleMapper, modifier: Modifier = Modifier) {
    Box(
//        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            val banner = article.banner!!
            if(banner.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth().height(250.dp),
//                shape = RoundedCornerShape(32.dp),
                    elevation = 0.dp
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(banner),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                        contentScale = ContentScale.FillBounds,
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
            }

            Text(
                article.ti!!,
                style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.primary),
            )
            Spacer(modifier = Modifier.size(8.dp))

            Text(
                article.location!!,
                style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.primary),
            )
        }
    }
}

@Composable
fun WidgetTitle(widgetTitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 1.dp, end = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(0.75f),
            text = widgetTitle,
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