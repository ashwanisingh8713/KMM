package com.ns.shopify.presentation.screen.address

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.ns.shopify.presentation.componets.AlertDialog
import com.ns.shopify.presentation.componets.CustomTextField
import com.ns.shopify.presentation.componets.DefaultBackArrow

internal class AddAddressScreen : Screen {
    @Composable
    override fun Content() {

        val viewModel = getScreenModel<AddAddressViewModel>()

        val navigation = LocalNavigator.current

        val onBackPress:()-> Unit = {
            navigation?.pop()
        }

        var address1 = mutableStateOf(TextFieldValue())

        var firstName = mutableStateOf(TextFieldValue())
        var lastName = mutableStateOf(TextFieldValue())
        var address2 = mutableStateOf(TextFieldValue())
        var city = mutableStateOf(TextFieldValue())
        var country = mutableStateOf(TextFieldValue())
        var company = mutableStateOf(TextFieldValue())
        var province = mutableStateOf(TextFieldValue())
        var phone = mutableStateOf(TextFieldValue())
        var pinCode = mutableStateOf(TextFieldValue())

        val onSubmitAddress:()->Unit = {
                    //address1, address2, city, country, company, firstName, lastName, province, phone, pinCode ->

            viewModel.addAddressRequest(
                address1 = address1.value.text,
                address2 = address2.value.text,
                city = city.value.text,
                country = country.value.text,
                company = company.value.text,
                firstName = firstName.value.text,
                lastName = lastName.value.text,
                phone = phone.value.text,
                province = province.value.text,
                zip = pinCode.value.text
            )

        }

        Scaffold(
            topBar = {
                Row ( modifier = Modifier.fillMaxWidth(0.7f),
                    horizontalArrangement = Arrangement.SpaceBetween
                )  {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .padding(top = 10.dp, start = 10.dp)
                    ) {
                        DefaultBackArrow{
                            onBackPress()
                        }
                    }

                    Text(
                        text = "Add new address",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 10.dp),
                    )
                }
            },
            bottomBar = {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.Center

                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .padding(top = 3.dp, bottom = 85.dp)
                            .height(45.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        onClick = {
                            onSubmitAddress()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ),
                    ) {
                        Text(text = "Submit Address", fontSize = 16.sp)
                    }
                }
            },
            content = {
                Column (modifier = Modifier.fillMaxSize().padding(it).verticalScroll(rememberScrollState())) {
                    AddressUI(viewModel = viewModel, onPopBack = onBackPress, address1 = address1, address2 = address2, city = city, country = country,
                        company = company, firstName = firstName, lastName = lastName, province = province,
                        phone = phone, pinCode = pinCode)
                }
            }
        )


    }

    @Composable
    fun AddressUI(viewModel: AddAddressViewModel, onPopBack:()->Unit,
                  address1: MutableState<TextFieldValue>, address2: MutableState<TextFieldValue>,
                  city: MutableState<TextFieldValue>, country: MutableState<TextFieldValue>,
                  company: MutableState<TextFieldValue>, firstName: MutableState<TextFieldValue>,
                  lastName: MutableState<TextFieldValue>, province: MutableState<TextFieldValue>,
                  phone: MutableState<TextFieldValue>, pinCode: MutableState<TextFieldValue>) {

        val state = viewModel.state.collectAsState()

        val firstNameErrorState = mutableStateOf(false)
        val lastNameErrorState = mutableStateOf(false)
        val phoneNumberErrorState = mutableStateOf(false)
        val pinCodeErrorState = mutableStateOf(false)

        /**/

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {

            CustomTextField(
                placeholder = "Address 1",
                label = "Address 1",
                keyboardType = KeyboardType.Text,
                visualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
                errorState = mutableStateOf(false),
                onChanged = {
                    address1.value = it
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
                    address2.value = it
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
                    city.value = it
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
                    country.value = it
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
                    company.value = it
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
                    firstName.value = it
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
                    lastName.value = it
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
                    province.value = it
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
                    phone.value = it
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
                    pinCode.value = it
                },
                icon = null,
                initialValue = ""
            )

        }

        val dialogErrorClicked:()->Unit = {
            viewModel.clearErrorState()
        }

        if(state.value.isLoaded) {
            AlertDialog(
                title = "Success!",
                message = "Address added successfully.",
                onClose = {
                    onPopBack()
                },

                )
        }
        if(state.value.error.isNotBlank()) {
            AlertDialog(
                title = "Error!",
                message = state.value.error,
                onClose = {
                    dialogErrorClicked()
                },

                )
        }

    }
}