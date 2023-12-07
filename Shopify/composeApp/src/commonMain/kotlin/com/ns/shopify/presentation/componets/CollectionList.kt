package com.ns.shopify.presentation.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ns.shopify.GetCollectionsQuery

/**
 * Created by Ashwani Kumar Singh on 07,December,2023.
 */

@Composable
fun CollectionList(
    list: List<GetCollectionsQuery.Node>,
    onClick: (GetCollectionsQuery.Node1) -> Unit,
    suggestionProductState: LazyListState = rememberLazyListState(),
    collectionState: LazyListState = rememberLazyListState(),
) {

    LazyColumn(state = collectionState, modifier = Modifier.padding(start = 15.dp, end = 15.dp)) {
        items(
            items = list
        ) { currentCollection ->

            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = currentCollection.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "See More", color = MaterialTheme.colors.primary)
            }

            Spacer(modifier = Modifier.height(8.dp))


            LazyRow(
                state = suggestionProductState,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(horizontal = 10.dp)
            ) {
                items(
                    items = currentCollection.products.nodes
                ) { product ->
                    ProductItem(product = product)
                }
            }

        }
    }

}


@Composable
fun ProductItem(product: GetCollectionsQuery.Node1) {
    Column {
        Box(
            modifier = Modifier
//                                .size(150.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    //onItemClick(state.product[it].id)
                },
            contentAlignment = Alignment.Center
        ) {
            NetworkImage(
                modifier = Modifier.size(height = 100.dp, width = 150.dp),
                url = "${product.featuredImage?.url}"
            )
        }
        Text(
            text = product.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(150.dp).padding(top = 5.dp)
        )

        Row(
            modifier = Modifier
                .width(150.dp)
                .fillMaxWidth().padding(top = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$ ${product.priceRange.minVariantPrice.amount}",
                fontWeight = FontWeight(600),
                color = MaterialTheme.colors.primary
            )

        }
    }

}