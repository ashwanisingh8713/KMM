
package ui.screens.home.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import domain.model.Article
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

/**
 * Displays Articles
 *
 * @param isLoading If true, shimmer placeholder will be displayed in place of the card's contents
 * @param article Post to be displayed
 */
@Composable
fun PostCard(isLoading: Boolean, article: Article, modifier: Modifier = Modifier) {
    Card(modifier.padding(vertical = 1.dp)) {
        Row(Modifier.fillMaxWidth().padding(16.dp)) {
            Column(Modifier.fillMaxWidth(0.7f).padding(end = 24.dp)) {
                Text(
                    text = article.ti,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.fillMaxWidth()
//                        .placeholder(isLoading, highlight = PlaceholderHighlight.shimmer()),
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "~ ${article.au}",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.Gray,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Light,
                    ),
                    modifier = Modifier.fillMaxWidth()
//                        .placeholder(isLoading, highlight = PlaceholderHighlight.shimmer()),
                )
            }
//            val painterResource = asyncPainterResource(data = article.im_thumbnail)
            val painterResource: Resource<Painter> = asyncPainterResource(article.im_thumbnail)


            Box(modifier = modifier.size(100.dp)) {
                KamelImage(
                    resource = painterResource,
                    contentDescription = "Profile",
                    onLoading = { progress -> CircularProgressIndicator(progress) },
                    onFailure = { exception ->

                    }
                )

            }

        }
    }
}
