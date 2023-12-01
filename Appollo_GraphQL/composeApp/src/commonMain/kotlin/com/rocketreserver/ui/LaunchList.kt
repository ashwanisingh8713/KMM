
package com.rocketreserver.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import com.apollographql.apollo3.api.ApolloResponse
import com.ns.shopify.LaunchListQuery
import com.ns.shopify.TripsBookedSubscription
import com.rocketreserver.http.apolloClient
import com.rocketreserver.vm.LaunchListViewModel
import com.seiko.imageloader.rememberAsyncImagePainter
import org.koin.core.component.KoinComponent

object LaunchList : Screen, KoinComponent {
    override val key: ScreenKey = uniqueScreenKey
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        val onLaunchClick:(launchId:String)->Unit = {
            navigator?.push(LaunchDetails(it))
        }

        val viewModel = rememberScreenModel { LaunchListViewModel() }
        val launchList by viewModel.successSectionList.collectAsState()
        val lazyColumnState = rememberLazyListState()

        LifecycleEffect(
            onStarted = {
                viewModel.makeListRequest()
            }
        )





        DataList(onLaunchClick, null, response = null, launchList, lazyColumnState)

    }
}

@Composable
fun DataList(onLaunchClick: (launchId: String) -> Unit,
             cursor: String? = null,
             response: ApolloResponse<LaunchListQuery.Data>? = null,
             launchList: List<LaunchListQuery.Launch> = emptyList(),
             lazyListState: LazyListState) {




    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), state= lazyListState) {
        items(launchList.size) {
            LaunchItem(launch = launchList[it], launchId = launchList[it].id, onClick = onLaunchClick)
            Divider(color = Color.Black, thickness = 1.dp)
        }
        item {
            if (response?.data?.launches?.hasMore == true) {
                LoadingItem()
//                cursor = response?.data?.launches?.cursor
            }
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun LaunchItem(launch: LaunchListQuery.Launch, launchId: String, onClick: (launchId: String) -> Unit) {

    ListItem(
        modifier = Modifier.clickable { onClick(launchId)},
        icon = {
            Box(
                modifier = Modifier
                    .size(68.dp, 68.dp)
                    .padding(end = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                val imgUrl = launch.mission?.missionPatch ?: ""
                Image(
                    painter = rememberAsyncImagePainter(imgUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 10.dp, start = 10.dp, bottom = 10.dp)
                        .width(110.dp)
                        .height(110.dp)
                )
            }
        },
        overlineText = {Text(text = "Overline - ${launch.mission?.name}")},
        text = {Text(text = "Text - ${launch.site}")},
        secondaryText = {Text(text = "Secondary - ID is ${launch.id}")},
//        trailing = {Text(text = "Trailing Site...")},
    )

/*ListItem(
        modifier = Modifier.clickable { onClick(launchId) },
        headlineText = {
            // Mission name
            Text(text = "Launch ${launch.id}")
        },
        supportingText = {
            // Site
            Text(text = "Site...")
        },
        leadingContent = {
            // Mission patch
            AsyncImage(
                modifier = Modifier.size(68.dp, 68.dp),
                model = launch.mission?.missionPatch,
                placeholder = painterResource(R.drawable.ic_placeholder),
                error = painterResource(R.drawable.ic_placeholder),
                contentDescription = "Mission patch"
            )
        }
    )*/
}

@Composable
private fun LoadingItem() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        CircularProgressIndicator()
    }
}


