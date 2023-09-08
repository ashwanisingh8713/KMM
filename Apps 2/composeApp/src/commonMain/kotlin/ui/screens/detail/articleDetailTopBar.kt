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
internal fun ArticleDetailTopBar(onBackPress: ()-> Unit) {
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
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        imageVector = Icons.Filled.SurroundSound,
                        contentDescription = "Localized description"
                    )
                }
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        imageVector = Icons.Filled.Comment,
                        contentDescription = "Localized description"
                    )
                }

                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        imageVector = Icons.Filled.BookmarkBorder,
                        contentDescription = "Localized description"
                    )
                }
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        imageVector = Icons.Filled.FormatSize,
                        contentDescription = "Localized description"
                    )
                }
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Localized description"
                    )
                }

                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        imageVector = Icons.Filled.LogoDev,
                        contentDescription = "Localized description"
                    )
                }
            }
        }
    )
}