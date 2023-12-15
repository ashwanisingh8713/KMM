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
import cafe.adriel.voyager.core.model.coroutineScope
import com.app.printLog
import com.ns.shopify.presentation.screen.product_detail.NewOptions
import com.ns.shopify.presentation.screen.product_detail.ProductDetailViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Ashwani Kumar Singh on 13,December,2023.
 */


@Composable
fun VariantsP(viewModel: ProductDetailViewModel) {

//    var refreshAllOption = MutableStateFlow(1)
//    val refreshAllStateFlow = refreshAllOption.asStateFlow()
    val optionRefreshRequired = viewModel.refreshAllStateFlow.collectAsState()

//    val allNewOptions by remember { mutableStateOf(mutableListOf<NewOptions>())}


    val onOptionSelection: (optionIndex: Int, itemIndex: Int) -> Unit =
        { selectedOptionIndex, selectedItemIndex ->

            viewModel.onOptionSelection(
                selectedOptionIndex = selectedOptionIndex,
                selectedItemIndex = selectedItemIndex
            )

            /*// Assigning new selected index for respective Option
            viewModel.allNewOptions[selectedOptionIndex].selectedIndex = selectedItemIndex

            if (selectedOptionIndex == viewModel.primaryOption) {
                viewModel.allNewOptions[viewModel.primaryOption].selectedIndex = selectedItemIndex
                viewModel.primarySelectedOption =
                    viewModel.allNewOptions[viewModel.primaryOption].values[selectedItemIndex]
            } else if(selectedOptionIndex == viewModel.secondOption) {
                viewModel.allNewOptions[viewModel.secondOption].selectedIndex = selectedItemIndex
                viewModel.secondSelectedOption =
                    viewModel.allNewOptions[viewModel.secondOption].values[selectedItemIndex]
            } else if(selectedOptionIndex == viewModel.thirdOption) {
                viewModel.allNewOptions[viewModel.thirdOption].selectedIndex = selectedItemIndex
                viewModel.thirdSelectedOption =
                    viewModel.allNewOptions[viewModel.thirdOption].values[selectedItemIndex]
            }
            viewModel.primarySelectedOption =
                viewModel.allNewOptions[viewModel.primaryOption].values[viewModel.allNewOptions[viewModel.primaryOption].selectedIndex]


            val filteredVariants = viewModel.variants.filter { variant ->
                val primaryTitle = viewModel.allNewOptions[viewModel.primaryOption].values[viewModel.allNewOptions[viewModel.primaryOption].selectedIndex].value
                variant.title.contains(
                    primaryTitle,
                    ignoreCase = true
                )
            }

            viewModel.allNewOptions[viewModel.secondOption].values.forEachIndexed { secOpIndex, secOp ->
                    if (viewModel.secondSelectedOption!!.value == secOp.value) {
                        // Third Option
                        viewModel.allNewOptions[viewModel.thirdOption].values.forEachIndexed { thiOpIndex, thiOp ->
                            val checkingTitle =
                                "${viewModel.primarySelectedOption?.value}${viewModel.Delimiter}${secOp.value}${viewModel.Delimiter}${thiOp.value}"
                            val thirdOptionValueAvailabilityCheckList = filteredVariants.filter {
                                it.title.contains(checkingTitle, true) && it.availableForSale
                            }
                            viewModel.allNewOptions[viewModel.thirdOption].values[thiOpIndex].availableForSale =
                                thirdOptionValueAvailabilityCheckList.isNotEmpty()

                        }

                        val checkingTitle =
                            "${viewModel.primarySelectedOption?.value}${viewModel.Delimiter}${secOp.value}"
                        val secondOptionValueAvailabilityCheckList = filteredVariants.filter {
                            it.title.contains(checkingTitle, true) && it.availableForSale
                        }
                        viewModel.allNewOptions[viewModel.secondOption].values[secOpIndex].availableForSale =
                            secondOptionValueAvailabilityCheckList.isNotEmpty()

                    } else {
                        val checkingTitle =
                            "${viewModel.primarySelectedOption?.value}${viewModel.Delimiter}${secOp.value}"
                        val secondOptionValueAvailabilityCheckList = filteredVariants.filter {
                            it.title.contains(checkingTitle, true) && it.availableForSale
                        }
                        viewModel.allNewOptions[viewModel.secondOption].values[secOpIndex].availableForSale =
                            secondOptionValueAvailabilityCheckList.isNotEmpty()
                    }

            }*/

//            refreshAllOption.value++

//            printLog(filteredVariants.toString())

            /*for (i in (selectedOptionIndex+1)..<viewModel.allNewOptions.size) { // Option
                printLog("\n\nOption^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")
                val nonPrimaryOption = viewModel.allNewOptions[i]
                val optionName = nonPrimaryOption.name
                printLog("Name :: $optionName")

                // This is for Values of the Option
                nonPrimaryOption.values.forEachIndexed { index, option ->
                    val optionTitle = option.value
                    printLog("Matching with Value  :: $optionTitle")
                    run second@{
                        filteredVariants.forEachIndexed { filteredIndex, filteredOption ->
                            val variantTitle = filteredOption.title
                            printLog("Filtered Combine Value  :: $variantTitle")
                            if (variantTitle.contains(optionTitle, ignoreCase = true)) {
                                val optionAvailableForSale = filteredOption.availableForSale
                                option.availableForSale = optionAvailableForSale
                                printLog("Variant If :: $optionTitle AvailableForSale $optionAvailableForSale\n")
                                printLog("")
                                return@second
                            } else {
                                printLog("Variant Else ::\n")
                                printLog("")
                            }
                        }
                    }
                }
            }*/
//            }


        } // Callback method

//    val refresh = refreshAllStateFlow.collectAsState()

    if (optionRefreshRequired.value!=null) {
        CreateOptionUI(
            allNewOptions = viewModel.allNewOptions,
            onOptionSelection = onOptionSelection
        )
    }
}

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

    Spacer(modifier = Modifier.height(10.dp))
    Row {
        Text(
            text = "Rs.",
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = "100",
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text = "Add to Cart")
    }
    Spacer(modifier = Modifier.height(50.dp))
}