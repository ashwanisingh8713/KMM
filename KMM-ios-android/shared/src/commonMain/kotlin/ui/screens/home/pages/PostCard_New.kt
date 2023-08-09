
package ui.screens.home.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Article
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import ui.screens.detail.DetailScreen
import ui.theme.AppTheme
import ui.theme.Theme

/**
 * Displays Articles
 *
 * @param isLoading If true, shimmer placeholder will be displayed in place of the card's contents
 * @param article Post to be displayed
 */
@Composable
fun PostCard_New(isLoading: Boolean, article: Article, modifier: Modifier = Modifier, onArticleClick: (article: Article) -> Unit) {

    Box(modifier = modifier.fillMaxWidth().fillMaxHeight(0.6f),
        contentAlignment = Alignment.Center) {

        KamelImage(
            modifier = Modifier.fillMaxWidth().height(200.dp),
            contentScale = ContentScale.FillBounds,
            resource = asyncPainterResource(article.im_thumbnail),
            contentDescription = "Profile",
            onLoading = { progress -> CircularProgressIndicator(modifier = Modifier.size(50.dp), progress = progress) },
            onFailure = { exception ->
                Text(text = "Error loading image :: ${article.ti} :: ${exception.message}")

            }
        )}


        Card(modifier = Modifier.padding(vertical = 1.dp) ) {

            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                Text(
                    text = article.sname,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = article.ti,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.fillMaxWidth(),
                    color = Theme.colors.onTertiary
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = article.le,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                    modifier = Modifier.fillMaxWidth(),
                    color = Theme.colors.onTertiary
                )
            }



    }

    ////////////////////////////////////////////////////////////////////////////////////////////////


}
