package com.ns.shopify.presentation.screen.product_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
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
import com.multiplatform.webview.util.KLogSeverity
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLData
import com.ns.shopify.ProductDetailQuery
import com.ns.shopify.presentation.componets.DefaultBackArrow
import com.ns.shopify.presentation.componets.NetworkImage
import com.ns.shopify.presentation.componets.VariantsP

/**
 * Created by Ashwani Kumar Singh on 12,December,2023.
 */
class NewProductDetailScreen(private val productId: String = "gid://shopify/Product/8051846119671") :
    Screen {

    @Composable
    override fun Content() {
        val navigation = LocalNavigator.current

        val viewModel = getScreenModel<ProductDetailViewModel>()
        val state: State<ProductDetailStates> = viewModel.state.collectAsState()

        val requestState by rememberUpdatedState(true)

        val popBack: () -> Unit = {
            navigation?.pop()
        }


        LaunchedEffect(requestState) {
            viewModel.getProductDetail(productId)
        }


        /*val product = state.value.success
        val compareAtPriceRange = product?.compareAtPriceRange

        val availableForSale = product?.availableForSale
        val maxVariantPrice = compareAtPriceRange?.maxVariantPrice
        val minVariantPrice = compareAtPriceRange?.minVariantPrice
        val variants = product?.variants
        val options = product?.options
        val featuredImage = product?.images*/

        if (state.value.isLoaded) {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                    .background(color = Color(0x8DB3B0B0)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ProductGallery(viewModel.featuredImage, popBack)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                        )
                        .padding(15.dp)
                ) {
                    // Product Title
                    ProductTitle(viewModel.title)
                    // Product Variants
                    VariantsP(viewModel)
                    // Product Description in TextView
                    ProductDescription(viewModel.state.value.success!!.description)
                    // Product Description in WebView
                    // ProductDescriptionInWeb(viewModel.state.value.success!!.descriptionHtml as String)
                }



                Spacer(modifier = Modifier.height(50.dp))
            }
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
                        color = if (selectedPicture == imageList[it].url as String) MaterialTheme.colors.primary else Color.Transparent,
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

@Composable
fun ProductDescription(description: String) {
    Text(text = description)
    Spacer(modifier = Modifier.height(50.dp))
}

/**
 * It shows product description in WebView,
 * But as of now it is not scrolling in iOS and there is issue with height in iOS.
 */
@Composable
fun ProductDescriptionInWeb(htmlDescription: String) {
    Box(Modifier.fillMaxWidth().height(500.dp)) {
        val webViewState = rememberWebViewStateWithHTMLData(htmlDescription)
        LaunchedEffect(Unit) {
            webViewState.webSettings.apply {
                zoomLevel = 1.0
                isJavaScriptEnabled = true
                logSeverity = KLogSeverity.Debug
                allowFileAccessFromFileURLs = true
                allowUniversalAccessFromFileURLs = true
                androidWebSettings.apply {
                    isAlgorithmicDarkeningAllowed = true
                    safeBrowsingEnabled = true
                    allowFileAccess = true

                }
            }
        }
        WebView(
            state = webViewState,
            modifier = Modifier.fillMaxSize().background(color = Color.Red),
            captureBackPresses = false,
        )
    }
}


