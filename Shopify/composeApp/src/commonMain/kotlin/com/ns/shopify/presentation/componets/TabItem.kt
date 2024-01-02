package com.ns.shopify.presentation.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab

/**
 * Created by Ashwani Kumar Singh on 05,December,2023.
 */
@Composable
fun RowScope.TabItem(tab: Tab) {
    val navigator = LocalTabNavigator.current
    val defaultIcon = rememberVectorPainter(image = Icons.Default.Home)

    NavigationBarItem(
        selected = navigator.current == tab,
        onClick = { navigator.current = tab },

        icon = {
            Icon(
                painter = tab.options.icon ?: defaultIcon,
                contentDescription = tab.options.title
            )
        },
        label = {
            Text(text = tab.options.title)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.CartTabItem(count: Int, tab: Tab) {
    val navigator = LocalTabNavigator.current
    val defaultIcon = rememberVectorPainter(image = Icons.Default.Home)
    NavigationBarItem(
        selected = navigator.current == tab,
        onClick = { navigator.current = tab },

        icon = {
            BadgedBox(
                badge = {
                    Text(
                        text = count.toString(),
                        fontSize = 10.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .size(22.dp)
                            .background(Color.Yellow, CircleShape)
                            .align(Alignment.CenterEnd)
                            .padding(start = 5.dp, top=5.dp)
//                            .offset(x = 8.dp, y = (-8).dp)
                    )
                },
                content = {
                    Icon(
                        painter = tab.options.icon ?: defaultIcon,
                        contentDescription = tab.options.title
                    )
                })
        },
        label = {
            Text(text = tab.options.title)
        }
    )

}

@Composable
fun RowScope.TabItemWithBadgeCount(
    count: Int = 0,
    selected: Boolean = false,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    tab: Tab
) {
    val navigator = LocalTabNavigator.current
    val defaultIcon = rememberVectorPainter(image = Icons.Default.Home)

    val tint =
        if (selected) MaterialTheme.colorScheme.primary else LocalContentColor.current.copy(alpha = 0.4f)

    NavigationBarItem(
        selected = navigator.current == tab,
        onClick = { navigator.current = tab },

        icon = {

            Row(
                Modifier
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Rounded.ShoppingCart,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp),
                    tint = tint
                )

                if (count > 0) {
                    Box(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .align(Alignment.CenterVertically),
//                        backgroundColor = MaterialTheme.colors.primary,
//                        contentColor = Color.White
                    ) {
                        Text(text = count.toString())
                    }
                }
            }
        },
        label = {
            Text(text = tab.options.title)
        }
    )
}






