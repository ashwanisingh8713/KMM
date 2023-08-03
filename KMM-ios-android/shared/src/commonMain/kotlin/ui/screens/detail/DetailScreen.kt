package ui.screens.detail

import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import domain.model.Article

/**
 * Created by Ashwani Kumar Singh on 03,August,2023.
 */

class DetailScreen(private val article: Article) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        ArticleDetailContents()
    }
}



@Composable
private fun ArticleDetailContents() {
    ArticleDetailTopBar()
    ArticleDetailBody()
    ArticleDetailBottomBar()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArticleDetailTopBar() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Simple TopAppBar",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
            ) {
                // Your content goes here
            }
        }
    )
}

@Composable
private fun ArticleDetailBody() {

}

@Composable
private fun ArticleDetailBottomBar() {

}

@Composable
private fun ArticleDetailBottomBarContents() {

}

@Composable
private fun ArticleDetailAds() {

}