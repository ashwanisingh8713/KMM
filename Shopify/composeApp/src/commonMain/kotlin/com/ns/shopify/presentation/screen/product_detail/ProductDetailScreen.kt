package com.ns.shopify.presentation.screen.product_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.apollographql.apollo3.api.Optional
import com.app.printLog
import com.ns.shopify.ProductDetailQuery
import com.ns.shopify.presentation.componets.ChildLayout
import com.ns.shopify.presentation.componets.DefaultBackArrow
import com.ns.shopify.presentation.componets.NetworkImage
import com.ns.shopify.presentation.componets.VariantsP
import com.ns.shopify.presentation.componets.VerticalScrollLayout
import com.ns.shopify.presentation.screen.cart.CartViewModel
import com.ns.shopify.type.CartInput
import com.ns.shopify.type.CartLineInput

/**
 * Created by Ashwani Kumar Singh on 12,December,2023.
 */
class NewProductDetailScreen(private val productId: String) :
    Screen {
        companion object {
            var productId = ""
        }

    @Composable
    override fun Content() {
        val navigation = LocalNavigator.current

        val viewModel = getScreenModel<ProductDetailViewModel>()
        val state: State<ProductDetailStates> = viewModel.state.collectAsState()

        val popBack: () -> Unit = {
            navigation?.pop()
        }

        val cartViewModel = getScreenModel<CartViewModel>()

        val addToCartEvent:(merchandiseId: String, quantity : Optional.Present<Int>) -> Unit = { merchandiseId, quantity ->
            cartViewModel.addToCart(merchandiseId, quantity)
        }

        if (state.value.isLoaded) {
            VerticalScrollLayout(
                modifier = Modifier,
                ChildLayout(
                    contentType = "It holds all Product Images",
                    content = {
                        if(viewModel.featuredImage.nodes.isNotEmpty()) ProductGallery(viewModel.featuredImage, popBack)
                    }
                ),
                ChildLayout(
                    contentType = "Divider",
                    content = {
                        Divider(
                            color = MaterialTheme.colorScheme.onBackground,
                            thickness = 1.dp)
                    }
                ),
                ChildLayout(
                    contentType = "ProductTitle",
                    content = {
                        ProductTitle(viewModel.title)
                    }
                ),
                ChildLayout(
                    contentType = "Product Variants",
                    content = {
                        VariantsP(viewModel, addToCartEvent)
                    }
                ),
                ChildLayout(
                    contentType = "Product Description",
                    content = {
                        ProductDetailComponent(viewModel.state.value.success!!.description)
                    }
                )
            )

        }
    }

}

@Composable
fun ProductGallery(images: ProductDetailQuery.Images, popBack: () -> Unit) {
    val imageList = images.nodes
    var selectedPicture by remember { mutableStateOf(imageList[0].url as String) }
    Box {
        NetworkImage(
            modifier = Modifier.height(250.dp).padding(10.dp), url = selectedPicture
        )
        Box(
            modifier = Modifier
                .size(50.dp)
                .padding(top = 10.dp, start = 10.dp)
                .align(Alignment.TopStart)
        ) {
            DefaultBackArrow(popBack)
        }
    }


    if (imageList.isNotEmpty()) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(imageList.size) {
                IconButton(
                    onClick = {
                        selectedPicture = imageList[it].url as String
                    }, modifier = Modifier.size(50.dp).border(
                        width = 1.dp,
                        color = if (selectedPicture == imageList[it].url as String) MaterialTheme.colorScheme.primary else Color.Transparent,
                        shape = RoundedCornerShape(10.dp)
                    ).background(Color.White, shape = RoundedCornerShape(10.dp))
                        .padding(5.dp).clip(RoundedCornerShape(10.dp))
                ) {
                    NetworkImage(
                        modifier = Modifier.size(50.dp), url = imageList[it].url as String
                    )

                }
            }

        }
        Spacer(modifier = Modifier.height(15.dp))

    }

}

@Composable
fun ProductTitle(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
    Spacer(modifier = Modifier.height(20.dp))
}





