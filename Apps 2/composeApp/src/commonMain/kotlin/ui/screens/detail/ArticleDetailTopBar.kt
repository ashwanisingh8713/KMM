package ui.screens.detail

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.LogoDev
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SurroundSound
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

/**
 * Created by Ashwani Kumar Singh on 08,September,2023.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ArticleDetailTopBar(onBackPress: ()-> Unit, onSharePress: ()-> Unit,
                                 onCommentPress: ()-> Unit, onBookmarkPress: ()-> Unit,
                                 onFontPress: ()-> Unit, onTextToSpeechPress: ()-> Unit,
                                 isBookmarked: Boolean, isTextToSpeechEnabled: Boolean) {
    TopAppBar(
        title = {

        },
        navigationIcon = {
            IconButton(onClick = { onBackPress() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            Row {
                IconButton(onClick = { onTextToSpeechPress() }) {
                    Icon(
                        imageVector = Icons.Filled.SurroundSound,
                        contentDescription = "Text To Speech"
                    )
                }
                IconButton(onClick = { onCommentPress() }) {
                    Icon(
                        imageVector = Icons.Filled.Comment,
                        contentDescription = "Comments"
                    )
                }

                IconButton(onClick = { onBookmarkPress() }) {
                    Icon(
                        imageVector = Icons.Filled.BookmarkBorder,
                        contentDescription = "Bookmark"
                    )
                }
                IconButton(onClick = { onFontPress() }) {
                    Icon(
                        imageVector = Icons.Filled.FormatSize,
                        contentDescription = "Font Size"
                    )
                }
                IconButton(onClick = { onSharePress() }) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Share"
                    )
                }

                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        imageVector = Icons.Filled.LogoDev,
                        contentDescription = "Premium Logo"
                    )
                }
            }
        }
    )
}