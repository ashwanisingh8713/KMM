package com.ns.shopify.presentation.screen.address

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.ns.shopify.presentation.componets.CustomDefaultBtn
import com.ns.shopify.presentation.componets.CustomTextField
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

internal class AddressScreen :  Screen {
    @Composable
    override fun Content() {
        AddressUI()
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun AddressUI() {
        Column ( modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
        ) {

            Text(text = "Add Address Screen")

            CustomTextField(
                placeholder = "Address 1",
                label = "Address 1",
                keyboardType = KeyboardType.Text,
                visualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
                errorState = mutableStateOf(false),
                onChanged = { },
                icon = null,
                initialValue = ""
            )

            CustomTextField(
                placeholder = "Address 2",
                label = "Address 2",
                keyboardType = KeyboardType.Text,
                visualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
                errorState = mutableStateOf(false),
                onChanged = { },
                icon = null,
                initialValue = ""
            )

            CustomTextField(
                placeholder = "Company",
                label = "Company",
                keyboardType = KeyboardType.Text,
                visualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
                errorState = mutableStateOf(false),
                onChanged = { },
                icon = null,
                initialValue = ""
            )

            CustomTextField(
                placeholder = "First name",
                label = "First name",
                keyboardType = KeyboardType.Text,
                visualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
                errorState = mutableStateOf(false),
                onChanged = { },
                icon = null,
                initialValue = ""
            )

            CustomTextField(
                placeholder = "Last name",
                label = "Last name",
                keyboardType = KeyboardType.Text,
                visualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
                errorState = mutableStateOf(false),
                onChanged = { },
                icon = null,
                initialValue = ""
            )

            CustomTextField(
                placeholder = "Province",
                label = "Province",
                keyboardType = KeyboardType.Text,
                visualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
                errorState = mutableStateOf(false),
                onChanged = { },
                icon = null,
                initialValue = ""
            )

            CustomTextField(
                placeholder = "Phone number",
                label = "Phone number",
                keyboardType = KeyboardType.Text,
                visualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
                errorState = mutableStateOf(false),
                onChanged = { },
                icon = null,
                initialValue = ""
            )

            CustomTextField(
                placeholder = "Zip Code",
                label = "Code",
                keyboardType = KeyboardType.Text,
                visualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
                errorState = mutableStateOf(false),
                onChanged = { },
                icon = null,
                initialValue = ""
            )

            CustomDefaultBtn(shapeSize = 50f, btnText = "Add Address") {

            }

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        MaterialTheme.colors.secondary,
                        shape = CircleShape
                    )
                    .clickable {

                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource("libres/images/empty.png"),
                    contentDescription = "Facebook Login Icon"
                )
            }

        }

    }
}