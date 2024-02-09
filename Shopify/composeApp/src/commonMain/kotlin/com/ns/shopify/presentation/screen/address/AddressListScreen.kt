package com.ns.shopify.presentation.screen.address

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.app.printLog
import com.ns.shopify.GetAddressQuery
import com.ns.shopify.data.storage.UserDataAccess
import com.ns.shopify.presentation.componets.DefaultBackArrow
import com.ns.shopify.presentation.componets.Loading
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 01,February,2024.
 */
class AddressListScreen : Screen, KoinComponent {

    @Composable
    override fun Content() {

        val addressListViewModel = getScreenModel<AddressListViewModel>()
        val addressState = addressListViewModel.addressState.collectAsState()

        val navigation = LocalNavigator.current

        val onBackPress: () -> Unit = {
            navigation?.pop()
        }

        val onAddAddress: () -> Unit = {
            navigation?.push(AddAddressScreen())
        }

        val onItemClick: (GetAddressQuery.Node) -> Unit = {
            addressListViewModel.saveAddressId(it.id)
            it.phone?.let { it1 ->
                addressListViewModel.savePhone(it1)
                addressListViewModel.saveFullAddress(it)
            }

//            onBackPress()
        }


        LaunchedEffect(true) {
            addressListViewModel.getAddressList()
        }

        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Back Arrow
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .padding(top = 10.dp, start = 10.dp)
                    ) {
                        DefaultBackArrow {
                            onBackPress()
                        }
                    }

                    // Page Title
                    Text(
                        text = "Address List",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    // Add Address Button
                    Button(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .height(30.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        onClick = {
                            onAddAddress()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ),
                    ) {
                        Text(
                            text = "Add",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W800,
                            textAlign = TextAlign.Center
                        )
                    }
                }

            },
            bottomBar = {

                Divider(thickness = 1.dp, color = Color.Gray)
            },
            content = {
                Box(modifier = Modifier.fillMaxSize().padding(it)) {
                    if (addressState.value.isLoading) {
                        AddressListLoading()
                    } else if (addressState.value.error.isNotEmpty()) {
                        AddressListError(addressState.value.error)
                    } else if (addressState.value.isLoaded) {
                        AddressListContent(
                            addressState.value.addresses,
                            onItemClick,
                            selectedAddressId = UserDataAccess.customerAddressId
                        )
                    }
                }
            })

    }


    @Composable
    fun AddressListLoading() {
        Loading()
    }

    @Composable
    fun AddressListError(error: String) {
        Text(
            text = error,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxSize()
                .padding(start = 16.dp, top = 8.dp, bottom = 2.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }

    @Composable
    fun AddressListEmpty() {
        Text(
            text = "No address is saved yet. Please add address.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxSize()
                .padding(start = 16.dp, top = 8.dp, bottom = 2.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }

    @Composable
    fun AddressListContent(
        addresses: List<GetAddressQuery.Node>,
        onItemClick: (GetAddressQuery.Node) -> Unit, selectedAddressId: String
    ) {
        val addressRev = addresses.reversed()
        if (addresses.isEmpty()) {
            AddressListEmpty()
        } else {
            LazyColumn {
                items(addressRev.size, itemContent = { index ->
                    AddressItem(
                        addressRev[index],
                        onItemClick = onItemClick,
                        selectedAddressId = selectedAddressId
                    )
                    Divider(thickness = 1.dp, color = Color.Gray)
                })
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }

    @Composable
    fun AddressItem(
        address: GetAddressQuery.Node,
        onItemClick: (GetAddressQuery.Node) -> Unit,
        selectedAddressId: String
    ) {
        val addressLine1 = address.address1
        val addressLine2 = address.address2
        val city = address.city
        val country = address.country
        val company = address.company
        val firstName = address.firstName
        val lastName = address.lastName
        val phone = address.phone
        val province = address.province
        val zip = address.zip
        val addressId = address.id

        val addressString =
            "Address : $addressLine1, $addressLine2, $city, $province, $country, $zip, $province Mobile : $phone"

        Box(modifier = Modifier.fillMaxWidth().clickable {
            onItemClick(address)
        }) {
            Row {

                printLog("LOLO selectedAddressId: $selectedAddressId")
                printLog("LOLO addressId: $addressId")

                val eliminateChars = "gid://shopify/MailingAddress/"
                val delimeter = "?"
                val temp_selectedAddressId = selectedAddressId.replace(eliminateChars, "")
                val temp_addressId = addressId.replace(eliminateChars, "")
                val numval_selectedAddressId = temp_selectedAddressId.split(delimeter)[0]
                val numval_addressId = temp_addressId.split(delimeter)[0]


                if (numval_selectedAddressId == numval_addressId) {
                    Checkbox(
                        checked = true,
                        onCheckedChange = { },
                        modifier = Modifier.padding(end = 16.dp)
                    )
                } else {
                    Checkbox(
                        checked = false,
                        onCheckedChange = { },
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }

                Text(
                    text = addressString,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth(0.4f)
                        .padding(start = 16.dp, top = 8.dp, bottom = 2.dp),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,

                    )

            }


        }
    }


}