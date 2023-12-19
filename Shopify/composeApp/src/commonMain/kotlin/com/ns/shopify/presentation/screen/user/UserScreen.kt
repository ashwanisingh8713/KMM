package com.ns.shopify.presentation.screen.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.ns.shopify.presentation.componets.CustomDefaultBtn
import com.ns.shopify.presentation.settings.SettingsViewModel
import org.koin.compose.rememberKoinInject

/**
 * Created by Ashwani Kumar Singh on 06,December,2023.
 */
internal class UserScreen : Screen {
    @Composable
    override fun Content() {
        UserOptionUI()
    }


    @Composable
    fun UserOptionUI() {
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

                Text("Cards", modifier = Modifier.weight(0.2f))
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
                Text("Purchased History", modifier = Modifier.weight(0.2f))
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

                Text("Address", modifier = Modifier.weight(0.2f))
                Icon(
                    painter = rememberVectorPainter(Icons.Default.ArrowForward),
                    contentDescription = null,
                    modifier = Modifier.weight(0.05f),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            val vm = rememberKoinInject<SettingsViewModel>()
            // Logout
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(Color(0x8DB3B0B0), shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        vm.saveLoggedInStatus(false)
                    }
                    .padding(start = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text("Logout", modifier = Modifier.weight(0.2f))
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