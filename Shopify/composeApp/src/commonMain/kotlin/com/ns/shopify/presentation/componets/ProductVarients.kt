package com.ns.shopify.presentation.componets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apollographql.apollo3.api.Optional
import com.app.printLog
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLData
import com.ns.shopify.ProductDetailQuery
import com.ns.shopify.presentation.screen.product_detail.NewOptions
import com.ns.shopify.presentation.screen.product_detail.ProductDetailViewModel

/**
 * Created by Ashwani Kumar Singh on 13,December,2023.
 */

@Composable
fun VariantsP(viewModel: ProductDetailViewModel, addToCartEvent:(merchandiseId: String, quantity : Optional.Present<Int>) -> Unit) {

    val optionRefreshRequired = viewModel.refreshAllStateFlow.collectAsState()

    val onOptionSelection: (optionIndex: Int, itemIndex: Int) -> Unit =
        { selectedOptionIndex, selectedItemIndex ->
            viewModel.onOptionSelection(
                selectedOptionIndex = selectedOptionIndex,
                selectedItemIndex = selectedItemIndex
            )
        }

    if (optionRefreshRequired.value != null) {
        CreateOptionUI(
            allNewOptions = viewModel.allNewOptions,
            onOptionSelection = onOptionSelection
        )
        PriceNdAddToCart(optionRefreshRequired.value!!, addToCartEvent)
    }

}


// Product Options Chip UIs
@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun CreateOptionUI(
    allNewOptions: List<NewOptions>,
    onOptionSelection: (optionIndex: Int, itemIndex: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        allNewOptions.forEachIndexed { optionIndex, option ->
            Text(
                text = option.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                option.values.forEachIndexed { itemIndex, item ->
                    Chip(
                        onClick = {
                            onOptionSelection(optionIndex, itemIndex)
                        },
                        border = BorderStroke(
                            ChipDefaults.OutlinedBorderSize,
                            Color.Red
                        ),
                        colors = ChipDefaults.chipColors(
                            backgroundColor = if (option.selectedIndex == itemIndex) Color.Yellow else Color.White,
                            contentColor = Color.Black,
                            disabledBackgroundColor = Color.LightGray,
                            disabledContentColor = Color.Gray
                        )
                    ) {
                        if (item.availableForSale) {
                            Text(
                                text = item.value,
                            )
                        } else {
                            Text(
                                text = item.value,
                                style = LocalTextStyle.current.copy(textDecoration = TextDecoration.LineThrough)
                            )
                        }
                    }
                }
            }
        }
    }


}


// Product Price, Add To Cart Btn
@Composable
fun PriceNdAddToCart(productVariant: List<ProductDetailQuery.Node1>, addToCartEvent:(merchandiseId: String, quantity : Optional.Present<Int>) -> Unit) {
    Spacer(modifier = Modifier.height(10.dp))
    Column(modifier = Modifier.padding(start = 15.dp, end = 15.dp)) {
        Row(modifier = Modifier.padding(start = 15.dp, end = 15.dp)) {
            Text(
                text = "Rs.",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = if (productVariant.isNotEmpty()) productVariant[0].price.amount.toString() else "",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                if (productVariant.isNotEmpty()) {
                    addToCartEvent(productVariant[0].id, Optional.Present(1))
                }
            },
            enabled = if (productVariant.isNotEmpty()) productVariant[0].availableForSale else false,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = if (productVariant.isNotEmpty()) if (productVariant[0].availableForSale) "Add to Cart" else "Out of stock" else "N/A")
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

