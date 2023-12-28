package com.ns.shopify.presentation.screen.address

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.ns.MainRes
import com.ns.shopify.presentation.componets.AlertDialog
import com.ns.shopify.presentation.componets.CustomDefaultBtn
import com.ns.shopify.presentation.componets.CustomTextField
import com.ns.shopify.presentation.componets.ErrorSuggestion
import com.ns.shopify.presentation.settings.SettingsViewModel
import io.github.skeptick.libres.compose.painterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.rememberKoinInject

internal class AddressScreen : Screen {
    @Composable
    override fun Content() {
        AddressUI()
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun AddressUI() {
        val viewModel = getScreenModel<AddressViewModel>()
        val state = viewModel.state.collectAsState()

        val firstNameErrorState = mutableStateOf(false)
        val lastNameErrorState = mutableStateOf(false)
        val phoneNumberErrorState = mutableStateOf(false)
        val pinCodeErrorState = mutableStateOf(false)

        var firstName by remember { mutableStateOf(TextFieldValue()) }
        var lastName by remember { mutableStateOf(TextFieldValue()) }
        var address1 by remember { mutableStateOf(TextFieldValue()) }
        var address2 by remember { mutableStateOf(TextFieldValue()) }
        var city by remember { mutableStateOf(TextFieldValue()) }
        var country by remember { mutableStateOf(TextFieldValue()) }
        var company by remember { mutableStateOf(TextFieldValue()) }
        var province by remember { mutableStateOf(TextFieldValue()) }
        var phoneNumber by remember { mutableStateOf(TextFieldValue()) }
        var pinCode by remember { mutableStateOf(TextFieldValue()) }

        Column(
            modifier = Modifier
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
                onChanged = {
                    address1 = it
                },
                icon = null,
                initialValue = ""
            )

            CustomTextField(
                placeholder = "Address 2",
                label = "Address 2",
                keyboardType = KeyboardType.Text,
                visualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
                errorState = mutableStateOf(false),
                onChanged = {
                    address2 = it
                },
                icon = null,
                initialValue = ""
            )

            CustomTextField(
                placeholder = "City",
                label = "City",
                keyboardType = KeyboardType.Text,
                visualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
                errorState = mutableStateOf(false),
                onChanged = {
                    city = it
                },
                icon = null,
                initialValue = ""
            )

            CustomTextField(
                placeholder = "Country",
                label = "Country",
                keyboardType = KeyboardType.Text,
                visualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
                errorState = mutableStateOf(false),
                onChanged = {
                    country = it
                },
                icon = null,
                initialValue = ""
            )


            CustomTextField(
                placeholder = "Company",
                label = "Company",
                keyboardType = KeyboardType.Text,
                visualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
                errorState = mutableStateOf(false),
                onChanged = {
                    company = it
                },
                icon = null,
                initialValue = ""
            )

            CustomTextField(
                placeholder = "First name",
                label = "First name",
                errorState = firstNameErrorState,
                keyboardType = KeyboardType.Text,
                visualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
                onChanged = {
                    firstName = it
                },
                icon = null,
                initialValue = ""
            )

            CustomTextField(
                placeholder = "Last name",
                label = "Last name",
                keyboardType = KeyboardType.Text,
                visualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
                errorState = lastNameErrorState,
                onChanged = {
                    lastName = it
                },
                icon = null,
                initialValue = ""
            )

            CustomTextField(
                placeholder = "Province",
                label = "Province",
                keyboardType = KeyboardType.Text,
                visualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
                errorState = mutableStateOf(false),
                onChanged = {
                    province = it
                },
                icon = null,
                initialValue = ""
            )

            CustomTextField(
                placeholder = "Phone number",
                label = "Phone number",
                keyboardType = KeyboardType.Text,
                visualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
                errorState = phoneNumberErrorState,
                onChanged = {
                    phoneNumber = it
                },
                icon = null,
                initialValue = ""
            )

            CustomTextField(
                placeholder = "Pin Code",
                label = "Pin Code",
                keyboardType = KeyboardType.Text,
                visualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
                errorState = pinCodeErrorState,
                onChanged = {
                    pinCode = it
                },
                icon = null,
                initialValue = ""
            )

            CustomDefaultBtn(shapeSize = 50f, btnText = "Add Address") {
                viewModel.addAddressRequest(
                    address1 = address1.text,
                    address2 = address2.text,
                    city = city.text,
                    country = country.text,
                    company = company.text,
                    firstName = firstName.text,
                    lastName = lastName.text,
                    phone = phoneNumber.text,
                    province = province.text,
                    zip = pinCode.text
                )
            }
        }

        if(state.value.isLoaded) {
            val vm = rememberKoinInject<SettingsViewModel>()
            AlertDialog(
                title = "Address Added!",
                message = "Added Successfully",
                onClose = {
                    vm.saveLoggedInStatus(true) // From here it sends callback to App.kt
                },

                )
        }
        if(state.value.error.isNotBlank()) {
            AlertDialog(
                title = "Error!",
                message = state.value.error,
                onClose = {
//                    dialogErrorClicked()
                },

                )
        }

    }
}