package ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.LogoDev
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SurroundSound
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.seiko.imageloader.rememberAsyncImagePainter
import domain.model.Article
import taboola.loadTaboolaWidget
import ui.screens.util.htmlDescription

/**
 * Created by Ashwani Kumar Singh on 03,August,2023.
 */

data class DetailScreen(private val article: Article) : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val onBackPress : () -> Unit = {
            navigator.pop()
        }
        ArticleDetailContents(onBackPress, article)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArticleDetailContents(onBackPress: ()-> Unit, article: Article) {
    Scaffold(
        topBar = {
            ArticleDetailTopBar(onBackPress)
        },
        bottomBar = {

        },
        content = {
            BoxWithConstraints(
                Modifier.padding(it),
                contentAlignment = Alignment.TopCenter
            ) {
                ArticleDetailBody(article = article)
            }

        }
    )

}


