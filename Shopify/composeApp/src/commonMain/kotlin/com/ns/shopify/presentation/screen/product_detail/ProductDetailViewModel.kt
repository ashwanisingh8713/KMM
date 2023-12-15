package com.ns.shopify.presentation.screen.product_detail

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.ns.shopify.ProductDetailQuery
import com.ns.shopify.domain.usecase.product.ProductDetailUsecase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 08,December,2023.
 */

data class OptionItem(
    var availableForSale: Boolean = false,
    val value: String,
)

data class NewOptions(
    val name: String,
    var selectedIndex: Int = 0,
    val values: MutableList<OptionItem>
)


class ProductDetailViewModel(private val productDetailUsecase: ProductDetailUsecase) : ScreenModel,
    KoinComponent {

    private val TAG = "ProductDetailViewModel"

    private val _stateOld = MutableStateFlow(OldProductDetailStates())
    val stateOld = _stateOld.asStateFlow()

    private val _state = MutableStateFlow(ProductDetailStates())
    val state = _state.asStateFlow()

    private val _refreshAllOption = MutableStateFlow<ProductDetailQuery.Node1?>(null)
    val refreshAllStateFlow = _refreshAllOption.asStateFlow()

    lateinit var title: String
    lateinit var variants: List<ProductDetailQuery.Node1>
    lateinit var options: List<ProductDetailQuery.Option>
    lateinit var featuredImage: ProductDetailQuery.Images
    val allNewOptions = mutableListOf<NewOptions>()

    // All filtration is being operated from First Option
    // First Option Index
    val primaryOption = 0
    val secondOption = 1
    val thirdOption = 2

    private lateinit var primarySelectedOption: OptionItem
    private lateinit var secondSelectedOption: OptionItem
    private lateinit var thirdSelectedOption: OptionItem
    val Delimiter = " / "

    /*var primarySelectedOptionIndex = 0
    var secondSelectedOptionIndex = 0
    var thirdSelectedOptionIndex = 0*/

    fun getProductDetail(id: String) {
        coroutineScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isLoaded = false
                )
            }
            productDetailUsecase(id)
                .onSuccess { response ->
                    val errorData = response.errors
                    if (!errorData.isNullOrEmpty()) { // Error Block
                        _state.update { its ->
                            val error = errorData[0].message
                            its.copy(
                                isLoading = false,
                                isLoaded = false,
                                error = error
                            )
                        }
                    } else {  // Success Block
                        val product = response.data?.product
                        if (product != null) {
                            variants = product.variants.nodes
                            options = product.options
                            featuredImage = product.images
                            title = product.title
                            _state.update { its ->
                                its.copy(
                                    isLoading = false,
                                    isLoaded = true,
                                    success = product
                                )
                            }
                            createNewOptions()
                        }
                    }
                }
                .onFailure { itError ->
                    _state.update { its ->
                        its.copy(
                            isLoading = false,
                            isLoaded = false,
                            error = itError.message.toString()
                        )
                    }
                }
        }
    }

    private fun createNewOptions() {
        options.forEachIndexed { optionIndex, option ->
            val newOptions = mutableListOf<OptionItem>()
            val values = option.values
            values.forEach { value ->
                newOptions.add(
                    OptionItem(
                        value = value,
                        availableForSale = (optionIndex == 0)
                    )
                )
            }

            allNewOptions.add(
                NewOptions(
                    name = option.name,
                    values = newOptions
                )
            )
        }
        primarySelectedOption =
            allNewOptions[primaryOption].values[allNewOptions[primaryOption].selectedIndex]
        secondSelectedOption =
            allNewOptions[secondOption].values[allNewOptions[secondOption].selectedIndex]
        thirdSelectedOption =
            allNewOptions[thirdOption].values[allNewOptions[thirdOption].selectedIndex]

        onOptionSelection(0, 0)
    }

    fun onOptionSelection(selectedOptionIndex: Int, selectedItemIndex: Int) {
        // Assigning new selected index for respective Option
        allNewOptions[selectedOptionIndex].selectedIndex = selectedItemIndex

        if (selectedOptionIndex == primaryOption) {
            allNewOptions[primaryOption].selectedIndex = selectedItemIndex
            primarySelectedOption =
                allNewOptions[primaryOption].values[selectedItemIndex]
        } else if (selectedOptionIndex == secondOption) {
            allNewOptions[secondOption].selectedIndex = selectedItemIndex
            secondSelectedOption =
                allNewOptions[secondOption].values[selectedItemIndex]
        } else if (selectedOptionIndex == thirdOption) {
            allNewOptions[thirdOption].selectedIndex = selectedItemIndex
            thirdSelectedOption =
                allNewOptions[thirdOption].values[selectedItemIndex]
        }
        primarySelectedOption =
            allNewOptions[primaryOption].values[allNewOptions[primaryOption].selectedIndex]


        val filteredVariants = variants.filter { variant ->
            val primaryTitle =
                allNewOptions[primaryOption].values[allNewOptions[primaryOption].selectedIndex].value
            variant.title.contains(
                primaryTitle,
                ignoreCase = true
            )
        }

        allNewOptions[secondOption].values.forEachIndexed { secOpIndex, secOp ->
            if (secondSelectedOption!!.value == secOp.value) {
                // Third Option
                allNewOptions[thirdOption].values.forEachIndexed { thiOpIndex, thiOp ->
                    val checkingTitle =
                        "${primarySelectedOption?.value}${Delimiter}${secOp.value}${Delimiter}${thiOp.value}"
                    val thirdOptionValueAvailabilityCheckList = filteredVariants.filter {
                        it.title.contains(checkingTitle, true) && it.availableForSale
                    }
                    allNewOptions[thirdOption].values[thiOpIndex].availableForSale =
                        thirdOptionValueAvailabilityCheckList.isNotEmpty()

                }

                val checkingTitle =
                    "${primarySelectedOption?.value}${Delimiter}${secOp.value}"
                val secondOptionValueAvailabilityCheckList = filteredVariants.filter {
                    it.title.contains(checkingTitle, true) && it.availableForSale
                }
                allNewOptions[secondOption].values[secOpIndex].availableForSale =
                    secondOptionValueAvailabilityCheckList.isNotEmpty()

            } else {
                val checkingTitle =
                    "${primarySelectedOption?.value}${Delimiter}${secOp.value}"
                val secondOptionValueAvailabilityCheckList = filteredVariants.filter {
                    it.title.contains(checkingTitle, true) && it.availableForSale
                }
                allNewOptions[secondOption].values[secOpIndex].availableForSale =
                    secondOptionValueAvailabilityCheckList.isNotEmpty()
            }

        }

        val selectedVariantOption = "${primarySelectedOption.value}${Delimiter}${secondSelectedOption.value}${Delimiter}${thirdSelectedOption.value}"

        val selectedVariant = filteredVariants.filter {
            it.title == selectedVariantOption
        }

        if (selectedVariant.isNotEmpty()) {
            _refreshAllOption.update {
                selectedVariant[0]
            }
        }
    }

}



