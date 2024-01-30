package com.ns.shopify.presentation.screen.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.ns.shopify.presentation.componets.DefaultBackArrow
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 30,January,2024.
 */
class PaymentCardScreen : Screen, KoinComponent {


    @Composable
    override fun Content() {
        val navigation = LocalNavigator.current

        val popBack: () -> Unit = {
            navigation?.pop()
        }

        PaymentScreen(popBack = popBack)
    }

    @Composable
    fun PaymentScreen(popBack: () -> Unit) {
        var cardHolder by remember { mutableStateOf("") }
        var cardNumber by remember { mutableStateOf("") }
        var expireMonth by remember { mutableStateOf("") }
        var expireYear by remember { mutableStateOf("") }
        var cvc by remember { mutableStateOf("") }
        var address by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {

            Row {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(top = 10.dp, start = 10.dp)
                ) {
                    DefaultBackArrow(popBack)
                }

                Text(
                    text = "Payment Credit Card",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp),
                )
            }

            OutlinedTextField(
                value = cardHolder,
                onValueChange = { cardHolder = it },
                label = { Text(text = "Cardholder Name") },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
            )

            OutlinedTextField(
                value = cardNumber,
                onValueChange = { cardNumber = it },
                label = { Text(text = "Credit Card Number") },
                maxLines = 1,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
            ) {
                OutlinedTextField(
                    value = expireMonth,
                    onValueChange = { expireMonth = it },
                    label = { Text(text = "Month") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                )

                OutlinedTextField(
                    value = expireYear,
                    onValueChange = { expireYear = it },
                    label = { Text(text = "Year") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                )
            }

            OutlinedTextField(
                value = cvc,
                onValueChange = { cvc = it },
                label = { Text(text = "CVC Code") },
                maxLines = 1,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
            )

            Text(
                text = "Address",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            )

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text(text = "Address") },
                maxLines = 4,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
            )

            Button(
                onClick = { /* Handle payment */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            ) {
                Text(text = "Pay Now")
            }
        }
    }


}