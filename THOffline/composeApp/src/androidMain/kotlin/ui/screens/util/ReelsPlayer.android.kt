package ui.screens.util

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ui.screens.home.ReelActions
import ui.screens.home.ReelInfo

/**
 * Created by Ashwani Kumar Singh on 02,November,2023.
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
actual fun ReelsPlayer(modifier: Modifier, index: Int, item: Reel, pagerState: PagerState) {

    val uri: Uri = Uri.parse("asset:///${item.video}")
//    val uri: Uri = Uri.parse(item.video)

    Box(modifier = modifier) {
        Player(
            index = index,
            uri = uri,
            pagerState = pagerState,
            url = item.video
        )
        Surface(
            color = Color.Transparent,
            contentColor = Color.White
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                var isShowMoreDescription by remember {
                    mutableStateOf(false)
                }

                ReelInfo(
                    item = item,
                    isShowMoreDescription = isShowMoreDescription,
                    onIsShowMoreDescriptionChange = {
                        isShowMoreDescription = !isShowMoreDescription
                    }
                )
                ReelActions(item)
            }
        }
    }

}