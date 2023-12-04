package ui.screens.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ModeComment
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.rounded.GraphicEq
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import ui.composables.ActionIcon
import com.seiko.imageloader.rememberImagePainter
import ui.screens.util.Reel
import ui.composables.ToggleButton
import ui.screens.util.ReelsPlayer
import ui.screens.util.dummyDataReels

/**
 * Created by Ashwani Kumar Singh on 02,November,2023.
 */
class ReelsScreen: Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        ReelsScreen()
    }


}



@Composable
fun ReelsScreen(
    modifier: Modifier = Modifier,
    reels: List<Reel> = remember {
        dummyDataReels
    },
) {
    Reels(items = reels, modifier = modifier)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Reels(
    items: List<Reel>,
    modifier: Modifier = Modifier,
) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        items.size ?: 0
    }

    VerticalPager(modifier = modifier
        .fillMaxSize()
        .background(Color.Black),
        state = pagerState,
        beyondBoundsPageCount = 1) { index ->
        val reel = items[index]

        Reel(index, reel, pagerState)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Reel(
    index: Int,
    item: Reel,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    ReelsPlayer(
        index = index,
        item = item,
        pagerState = pagerState,
        modifier = modifier,
    )
}

@Composable
fun ReelActions(item: Reel, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(top = 24.dp, end = 12.dp, bottom = 12.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column {
                var isButtonFavoriteChecked by remember { mutableStateOf(false) }
                ToggleButton(
                    checkedImageVector = Icons.Outlined.Favorite,
                    unCheckedImageVector = Icons.Outlined.FavoriteBorder,
                    colorIcon = LocalContentColor.current,
                    isChecked = isButtonFavoriteChecked,
                    onClick = { isButtonFavoriteChecked = !isButtonFavoriteChecked }
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.likes,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = androidx.compose.material.MaterialTheme.typography.body2,
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column {
                ActionIcon(imageVector = Icons.Outlined.ModeComment)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = item.comment,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = androidx.compose.material.MaterialTheme.typography.body2,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ActionIcon(imageVector = Icons.Outlined.Send)
            Spacer(modifier = Modifier.height(16.dp))
            ActionIcon(imageVector = Icons.Outlined.MoreVert)
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
                modifier = Modifier,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, Color.White),
            ) {
//                Image(
//                    painter = rememberImagePainter(data = item.music.profile),
//                    contentDescription = null,
//                    modifier = Modifier.size(35.dp),
//                    contentScale = ContentScale.Crop
//                )
            }
        }
    }
}

@Composable
fun RowScope.ReelInfo(
    item: Reel,
    isShowMoreDescription: Boolean,
    onIsShowMoreDescriptionChange: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(start = 12.dp, bottom = 12.dp, end = 32.dp, top = 24.dp)
            .weight(1F)
            .animateContentSize(
                animationSpec = tween(400)
            ),
    ) {
        ReelInfoUser(
            item = item
        )
        Spacer(modifier = Modifier.heightIn(16.dp))
        ReelInfoDescription(
            item = item,
            isShowMoreDescription = isShowMoreDescription,
            onIsShowMoreDescriptionChange = onIsShowMoreDescriptionChange,
        )
        Spacer(modifier = Modifier.height(16.dp))
        ReelInfoMusic(item)
    }
}

@Composable
fun ReelInfoMusic(item: Reel, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        androidx.compose.material.Icon(
            imageVector = Icons.Rounded.GraphicEq,
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = item.music.name,
            style = androidx.compose.material.MaterialTheme.typography.subtitle1.copy(fontSize = 14.sp)
        )
        Spacer(modifier = Modifier.width(3.dp))
        Surface(
            modifier = Modifier
                .size(2.dp),
            shape = CircleShape,
            color = Color.White,
        ) { }
        Spacer(modifier = Modifier.width(3.dp))
        androidx.compose.material.Text(
            text = if (item.music.isOriginalAudio) "Original Audio" else item.music.author,
            style = androidx.compose.material.MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
fun ReelInfoDescription(
    item: Reel,
    isShowMoreDescription: Boolean,
    onIsShowMoreDescriptionChange: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val description = buildAnnotatedString {
        withStyle(style = androidx.compose.material.MaterialTheme.typography.subtitle1.toSpanStyle()) {
            append(item.description)
        }
        append("  ")
        withStyle(
            style = androidx.compose.material.MaterialTheme.typography.body2.copy(
                color = Color.White.copy(
                    alpha = .6F
                )
            ).toSpanStyle()
        ) {
            val tags = item.tags.joinToString(" ")
            append(tags)
        }
    }

    androidx.compose.material.Text(
        text = description,
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                onIsShowMoreDescriptionChange.invoke()
            },
        maxLines = if (isShowMoreDescription) Int.MAX_VALUE else 1,
        overflow = if (isShowMoreDescription) TextOverflow.Clip else TextOverflow.Ellipsis
    )
}

@Composable
fun ReelInfoUser(item: Reel, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Surface(
            shape = CircleShape,
        ) {
//            Image(
//                painter = rememberImagePainter(data = item.user.profile),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(30.dp),
//                contentScale = ContentScale.Crop,
//            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = item.user.username,
            style = androidx.compose.material.MaterialTheme.typography.subtitle2.copy(color = Color.White)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Surface(
            shape = androidx.compose.material.MaterialTheme.shapes.medium,
            color = Color.Transparent,
            contentColor = Color.White,
            border = BorderStroke(1.dp, Color.White),
        ) {
            androidx.compose.material.Text(
                text = if (item.user.follow) "Unfollow" else "Follow",
                modifier = Modifier
                    .padding(2.dp)
                    .padding(horizontal = 6.dp),
                style = androidx.compose.material.MaterialTheme.typography.body2,
            )
        }
    }
}