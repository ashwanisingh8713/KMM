package com.ns.shopify.presentation.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.ns.shopify.GetCollectionsQuery
import com.ns.shopify.presentation.componets.CollectionList
import com.ns.shopify.presentation.componets.Loading
import com.ns.shopify.presentation.screen.product_detail.NewProductDetailScreen

/**
 * Created by Ashwani Kumar Singh on 06,December,2023.
 */

internal class HomeScreen(
    private val state: State<HomeState>,
    private val getCollection: () -> Unit,
) : Screen {


    @Composable
    override fun Content() {
        val navController = LocalNavigator.currentOrThrow



        val openProductDetail:(GetCollectionsQuery.Node1) -> Unit = {
//            navController.push(ProductDetailScreen())

//            val viewModel = getScreenModel<ProductDetailViewModel>()
//            val state: State<ProductDetailStates> = viewModel.state.collectAsState()
            NewProductDetailScreen.productId = it.id
            navController.push(NewProductDetailScreen(productId = it.id))
        }

        HomeScreen(openProductDetail = openProductDetail)
    }

    @Composable
    fun HomeScreen(openProductDetail: (GetCollectionsQuery.Node1) -> Unit) {
        var selectedTabIndex by remember { mutableStateOf(0) }

        val tabs = listOf(TopBarItem.General,TopBarItem.Business,TopBarItem.Traveling)
        Column(
            modifier = Modifier.padding(10.dp).padding(bottom = 80.dp)
        ) {

            // Top Tab Bar
            /*TabRow(
                selectedTabIndex = selectedTabIndex
            ) {
                tabs.forEachIndexed { index, topBarItem ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            //loadNews(selectedTabIndex)
                            getCollection()
                        },
                        icon = {
                            Icon(
                                imageVector = topBarItem.icon,
                                contentDescription = topBarItem.label
                            )
                        },
                        text = {
                            Text(text = topBarItem.label)
                        }
                    )
                }
            }*/

            if (state.value.error.isNotBlank()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.value.error, fontSize = 25.sp)
                }
            }
            if (state.value.isLoading) {
                Loading()
            }
            if (state.value.isLoaded) {
                val navController = LocalNavigator.currentOrThrow

                CollectionList(list = state.value.success, onItemClick = openProductDetail)

                /*CollectionList(list = state.value.success, onItemClick = { product ->
                    // Open Product Detail Page
                    navController.push(ProductDetailScreen())
                })*/
            }
        }
    }


}