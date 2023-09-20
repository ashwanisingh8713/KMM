package ui.screens.home.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.seiko.imageloader.rememberAsyncImagePainter
import domain.mapper.ArticleMapper

/**
 * Displays Articles
 *
 * @param isLoading If true, shimmer placeholder will be displayed in place of the card's contents
 * @param article Post to be displayed
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCard_New(
    isLoading: Boolean,
    article: ArticleMapper,
    modifier: Modifier = Modifier,
    onArticleClick: (article: ArticleMapper) -> Unit
) {
    val navigator = LocalNavigator.currentOrThrow
    Card(modifier = Modifier.padding(vertical = 1.dp), onClick = {
        onArticleClick(article)
    }) {
//        val imgUrl = if(article.me.isNotEmpty())
//            article.me[0].im
//        else {
//            article.im_thumbnail_v2!!
//        }

        Column(modifier = modifier) {
//            if(imgUrl.isNotEmpty()) {
//                Box(
//                    modifier = modifier.fillMaxWidth().fillMaxHeight(0.6f),
//                    contentAlignment = Alignment.Center,
//                ) {
//                    Image(
//                        painter = rememberAsyncImagePainter(imgUrl),
//                        contentDescription = null,
//                        modifier = Modifier.fillMaxWidth().height(200.dp),
//                        contentScale = ContentScale.FillBounds,
//                    )
//                }
//            }

            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                Text(
                    text = article.secName!!,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = article.ti!!,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.fillMaxWidth(),
//                    color = Theme.colors.onTertiary
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = article.le!!,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                    modifier = Modifier.fillMaxWidth(),
//                    color = Theme.colors.onTertiary
                )
            }


        }

        ////////////////////////////////////////////////////////////////////////////////////////////////
    }

}
