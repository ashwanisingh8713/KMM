package ui.screens.home.pages

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import domain.model.Article

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
    article: Article,
    modifier: Modifier = Modifier,
    onArticleClick: (article: Article) -> Unit
) {

    Card(modifier = Modifier.padding(vertical = 1.dp), onClick = { onArticleClick(article) }) {
        Column(modifier = modifier) {
            Box(
                modifier = modifier.fillMaxWidth().fillMaxHeight(0.6f),
                contentAlignment = Alignment.Center,
            ) {
                /*KamelImage(
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentScale = ContentScale.FillBounds,
                    resource = asyncPainterResource(article.im_thumbnail),
                    contentDescription = "Profile",
                    onLoading = { progress ->
                        CircularProgressIndicator(
                            modifier = Modifier.then(
                                Modifier.size(32.dp)
                            )
                        )
                    },
                    onFailure = { exception ->
                        KamelImage(
                            modifier = Modifier.fillMaxWidth().height(200.dp),
                            contentScale = ContentScale.FillBounds,
                            resource = asyncPainterResource("https://appsearch.thehindu.com/admin/assets/images/icons/th/Light/xxhdpi/banner-b.png"),
                            contentDescription = "Profile",
                            onLoading = { progress ->
                                CircularProgressIndicator(
                                    modifier = Modifier.then(
                                        Modifier.size(32.dp)
                                    ), progress = progress
                                )
                            },
                            onFailure = { exception ->
                                // https://appsearch.thehindu.com/admin/assets/images/icons/th/Light/xxhdpi/banner-b.png
                                Text(text = "Error loading image :: ${article.ti} :: ${exception.message}")

                            }
                        )

                    }
                )*/
            }




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
//                    color = Theme.colors.onTertiary
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = article.le,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                    modifier = Modifier.fillMaxWidth(),
//                    color = Theme.colors.onTertiary
                )
            }


        }

        ////////////////////////////////////////////////////////////////////////////////////////////////
    }

}
