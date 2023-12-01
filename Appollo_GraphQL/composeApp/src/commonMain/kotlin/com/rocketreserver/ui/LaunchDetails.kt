package com.rocketreserver.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.exception.ApolloException
import com.ns.shopify.BookTripMutation
import com.ns.shopify.CancelTripMutation
import com.ns.shopify.LaunchDetailsQuery
import com.ns.shopify.TripsBookedSubscription
import com.rocketreserver.http.TokenRepository
import com.rocketreserver.http.apolloClient
import com.rocketreserver.ui.LaunchDetailsState.ApplicationError
import com.rocketreserver.ui.LaunchDetailsState.Loading
import com.rocketreserver.ui.LaunchDetailsState.ProtocolError
import com.rocketreserver.ui.LaunchDetailsState.Success
import kotlinx.coroutines.launch

private sealed interface LaunchDetailsState {
    object Loading : LaunchDetailsState
    data class ProtocolError(val exception: ApolloException) : LaunchDetailsState
    data class ApplicationError(val errors: List<Error>) : LaunchDetailsState
    data class Success(val data: LaunchDetailsQuery.Data) : LaunchDetailsState
}

class LaunchDetails(private val launchId: String) : Screen {
    override val key: ScreenKey = uniqueScreenKey



    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        val navigateToLogin:()-> Unit = {
            navigator?.push(Login())
        }


        val snackbarHostState = remember { SnackbarHostState() }
        val tripBookedFlow = remember { apolloClient.subscription(TripsBookedSubscription()).toFlow() }
        val tripBookedResponse: ApolloResponse<TripsBookedSubscription.Data>? by tripBookedFlow.collectAsState(initial = null)
        LaunchedEffect(tripBookedResponse) {
            if (tripBookedResponse == null) return@LaunchedEffect
            val message = when (tripBookedResponse!!.data?.tripsBooked) {
                null -> "Subscription error"
                -1 -> "Trip cancelled"
                else -> "Trip booked! 🚀"
            }
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { paddingValues ->
            Box(Modifier.padding(paddingValues)) {
                DataLaunchDetails(launchId = launchId, navigateToLogin = navigateToLogin)
            }
        }


    }
}

@Composable
fun DataLaunchDetails(launchId: String, navigateToLogin: () -> Unit) {
    var state by remember { mutableStateOf<LaunchDetailsState>(Loading) }
    LaunchedEffect(Unit) {
        state = try {
            val response = apolloClient.query(LaunchDetailsQuery(launchId)).execute()
            if (response.hasErrors()) {
                ApplicationError(response.errors!!)
            } else {
                Success(response.data!!)
            }
        } catch (e: ApolloException) {
            ProtocolError(e)
        }
    }
    when (val s = state) {
        Loading -> Loading()
        is ProtocolError -> ErrorMessage("Oh no... A protocol error happened: ${s.exception.message}")
        is ApplicationError -> ErrorMessage(s.errors[0].message)
        is Success -> LaunchDetails(s.data, navigateToLogin)
    }
}

@Composable
private fun LaunchDetails(
    data: LaunchDetailsQuery.Data,
    navigateToLogin: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Mission patch
            /*AsyncImage(
                modifier = Modifier.size(160.dp, 160.dp),
                model = data.launch?.mission?.missionPatch,
                placeholder = painterResource(R.drawable.ic_placeholder),
                error = painterResource(R.drawable.ic_placeholder),
                contentDescription = "Mission patch"
            )*/

            Spacer(modifier = Modifier.size(16.dp))

            Column {
                // Mission name
                Text(
                    style = MaterialTheme.typography.headlineMedium,
                    text = data.launch?.mission?.name ?: ""
                )

                // Rocket name
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    text = data.launch?.rocket?.name?.let { "🚀 $it" } ?: "",
                )

                // Site
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.titleMedium,
                    text = data.launch?.site ?: "",
                )
            }
        }
        // Book button
        var loading by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        var isBooked by remember { mutableStateOf(data.launch?.isBooked == true) }
        Button(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth(),
            enabled = !loading,
            onClick = {
                loading = true
                scope.launch {
                    val ok = onBookButtonClick(
                        launchId = data.launch?.id ?: "",
                        isBooked = isBooked,
                        navigateToLogin = navigateToLogin
                    )
                    if (ok) {
                        isBooked = !isBooked
                    }
                    loading = false
                }
            }
        ) {
            if (loading) {
                SmallLoading()
            } else {
                Text(text = if (!isBooked) "Book now" else "Cancel booking")
            }
        }
    }
}

private suspend fun onBookButtonClick(launchId: String, isBooked: Boolean, navigateToLogin: () -> Unit): Boolean {
    if (TokenRepository.getToken() == null) {
        navigateToLogin()
        return false
    }
    val mutation = if (isBooked) {
        CancelTripMutation(id = launchId)
    } else {
        BookTripMutation(id = launchId)
    }
    val response = try {
        apolloClient.mutation(mutation).execute()
    } catch (e: ApolloException) {
        return false
    }

    if (response.hasErrors()) {
        return false
    }
    return true
}

@Composable
private fun ErrorMessage(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = text)
    }
}

@Composable
private fun Loading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SmallLoading() {
    CircularProgressIndicator(
        modifier = Modifier.size(24.dp),
        color = LocalContentColor.current,
        strokeWidth = 2.dp,
    )
}