package com.ns.shopify.presentation.screen.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 18,December,2023.
 */
class CartScreen:Screen, KoinComponent {
    @Composable
    override fun Content() {
        MoreOptionUI()
    }

    @Composable
    fun MoreOptionUI() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)

                    .background(Color(0x8DB3B0B0), shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {

                    }
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text("Notification", modifier = Modifier.weight(0.2f))
                Icon(
                    painter = rememberVectorPainter(Icons.Default.ArrowForward),
                    contentDescription = null,
                    modifier = Modifier.weight(0.05f),
                    tint = MaterialTheme.colorScheme.primary
                )
            }


            Spacer(modifier = Modifier.height(15.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)

                    .background(Color(0x8DB3B0B0), shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {

                    }
                    .padding(start = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Settings", modifier = Modifier.weight(0.2f))
                Icon(
                    painter = rememberVectorPainter(Icons.Default.ArrowForward),
                    contentDescription = null,
                    modifier = Modifier.weight(0.05f),
                    tint = MaterialTheme.colorScheme.primary
                )
            }


            Spacer(modifier = Modifier.height(15.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)

                    .background(Color(0x8DB3B0B0), shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {

                    }
                    .padding(start = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text("Help Center", modifier = Modifier.weight(0.2f))
                Icon(
                    painter = rememberVectorPainter(Icons.Default.ArrowForward),
                    contentDescription = null,
                    modifier = Modifier.weight(0.05f),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(15.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(Color(0x8DB3B0B0), shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {

                    }
                    .padding(start = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text("FAQs", modifier = Modifier.weight(0.2f))
                Icon(
                    painter = rememberVectorPainter(Icons.Default.ArrowForward),
                    contentDescription = null,
                    modifier = Modifier.weight(0.05f),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

        }
    }

}