package com.ns.shopify.presentation.screen.cart

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ns.shopify.data.utils.amountFormatter
import com.ns.shopify.presentation.componets.NetworkImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CartItem(
    cartUiData: UserCartUiData,
    onCartItemClicked: (UserCartUiData) -> Unit,
    onIncrement: (UserCartUiData) -> Unit,
    onDecrement: (UserCartUiData) -> Unit,
) {
    Card(
        modifier = Modifier
            .combinedClickable(
                onClick = { onCartItemClicked(cartUiData) },
                onLongClick = {

                },
            )
            .fillMaxWidth()
            .padding(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        ),
        shape = RoundedCornerShape(10.dp),
    ) {
        val amount = cartUiData.price * cartUiData.quantity
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            NetworkImage(
                url = cartUiData.imageUrl,
                modifier = Modifier
                    .size(60.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    modifier = Modifier
                        .widthIn(max = 150.dp),
                    text = cartUiData.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = amountFormatter(cartUiData.currencyCode, amount))
                Spacer(modifier = Modifier.height(4.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(
//                    painter = MainRes.image.ic_minus.painterResource(),
                    painter = rememberVectorPainter(image = Icons.Outlined.MoreVert),
                    contentDescription = "Decrement",
                    modifier = Modifier.clickable { onDecrement(cartUiData) },
                )
                Text(modifier = Modifier.width(25.dp),
                    textAlign = TextAlign.Center,
                    text = cartUiData.quantity.toString(),
                    fontWeight = FontWeight.Bold, )
                Icon(
//                    painter = MainRes.image.ic_plus.painterResource(),
                    painter = rememberVectorPainter(image = Icons.Outlined.AddCircle),
                    contentDescription = "Increment",
                    modifier = Modifier.clickable { onIncrement(cartUiData) },
                )


            }
        }
    }
}
