package ui.tabNavigation.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

data class InnerScreen(
    val index: Int,
    val wrapContent: Boolean = false
) : Screen {

    override val key = uniqueScreenKey

    @Composable
    override fun Content() {


        val navigator = LocalNavigator.currentOrThrow

        Column(

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(color = Color.Red).run {
                if (wrapContent) padding(vertical = 16.dp).wrapContentHeight()
                else fillMaxSize()
            }
        ) {
            Text(
                text = "Screen #$index",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                Button(
                    enabled = navigator.canPop,
                    onClick = navigator::pop,
                    modifier = Modifier.weight(.5f)
                ) {
                    Text(text = "Pop")
                }

                Spacer(modifier = Modifier.weight(.1f))

                Button(
                    onClick = { navigator.push(InnerScreen(index.inc(), wrapContent)) },
                    modifier = Modifier.weight(.5f)
                ) {
                    Text(text = "Push")
                }

                Spacer(modifier = Modifier.weight(.1f))

                Button(
                    onClick = { navigator.replace(InnerScreen(index.inc(), wrapContent)) },
                    modifier = Modifier.weight(.5f)
                ) {
                    Text(text = "Replace")
                }
            }

            LazyColumn(
                modifier = Modifier.height(100.dp)
            ) {
                items(100) {
                    Text("Item #$it")
                }
            }
        }
    }
}
