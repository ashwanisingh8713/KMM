package com.ns.shopify.presentation.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

/**
 * Created by Ashwani Kumar Singh on 07,December,2023.
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun NetworkImage(
    modifier: Modifier = Modifier,
    url: String,
    shape: Dp = 12.dp
) {
    val painter = asyncPainterResource(url)

    KamelImage(
        resource = painter,
        contentDescription = "image",
        modifier = modifier.clip(RoundedCornerShape(shape)),
        contentScale = ContentScale.FillBounds,
        onFailure = {
            /*Image(
                painter = painterResource("img/empty.png"),
                contentDescription = "empty_image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )*/
            val emptyPainter = asyncPainterResource("https://samplelib.com/lib/preview/png/sample-clouds2-400x300.png")
            KamelImage(
                resource = emptyPainter,
                contentDescription = "image",
                modifier = modifier.clip(RoundedCornerShape(shape)),
                contentScale = ContentScale.FillBounds,
                onLoading = {
                    Loading(modifier = Modifier.size(30.dp))
                },
            )
        },
        onLoading = {
            Loading(modifier = Modifier.size(30.dp))
        },
    )
}