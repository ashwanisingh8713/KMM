package ui.pager

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.ui.unit.dp
import androidx.compose.material3.OutlinedCard
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Created by Ashwani Kumar Singh on 25,July,2023.
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizPager() {
    // Display 10 items
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
    ) {
        10
    }
    HorizontalPager(state = pagerState) { page ->
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Start
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedCard {
                Column(
                    modifier = Modifier.fillMaxSize(0.5f).padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    // Our page content
                    pagerItem(page)
                }
            }
            Spacer(modifier = Modifier.width(28.dp))
        }



    }
}

@Composable
private fun pagerItem(page: Int) {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Title : $page",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "From your recent ones : $page",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    /* Do something! */
                }
            ) {
                Text("Buy : $page")
            }
        }
    }
}




