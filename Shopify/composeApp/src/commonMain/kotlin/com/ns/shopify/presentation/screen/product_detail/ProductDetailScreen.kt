package com.ns.shopify.presentation.screen.product_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.ns.shopify.di.viewModelModule
import com.ns.shopify.presentation.componets.DefaultBackArrow
import com.ns.shopify.presentation.componets.NetworkImage
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.unloadKoinModules


/**
 * Created by Ashwani Kumar Singh on 08,December,2023.
 */


class ProductDetailScreen : Screen {

    @Composable
    override fun Content() {
        val productDetailViewModel = getScreenModel<ProductDetailViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        ProductDetailScreen(viewModel = productDetailViewModel, popBack = {
            navigator.pop()
        })

    }

    @Composable
    fun ProductDetailScreen(
        viewModel: ProductDetailViewModel,
        popBack: () -> Unit
    ) {
        val state = viewModel.state.value
        /*if (state.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else */
        if (state.success != null) {
            val product = state.success
            var colorSelected by remember { mutableStateOf(product.colors[product.colors.size - 1]) }
            var selectedPicture by remember { mutableStateOf(product.images[0]) }
            var quantity by remember { mutableStateOf(1) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0x8DB3B0B0)),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp, top = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            popBack()
                        },
                        modifier = Modifier
                            .background(color = Color.White, shape = CircleShape)
                            .clip(CircleShape)

                    ) {
                        DefaultBackArrow(popBack)
                    }
                    Row(
                        modifier = Modifier
                            .width(70.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                            .padding(3.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        horizontalArrangement = Arrangement.spacedBy(
                            4.dp,
                            Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Rating",//product.rating.toString(),
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Image(
                            painter = rememberVectorPainter(image = Icons.Default.Favorite),
                            contentDescription = "empty_image",
                            modifier = Modifier.size(30.dp),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                }

                //image
                NetworkImage(
                    modifier = Modifier
                        .size(250.dp)
                        .padding(10.dp),
                    url = selectedPicture
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    items(product.images.size) {
                        IconButton(
                            onClick = {
                                selectedPicture = product.images[it]
                            },
                            modifier = Modifier
                                .size(50.dp)
                                .border(
                                    width = 1.dp,
                                    color = if (selectedPicture == product.images[it]) MaterialTheme.colors.primary else Color.Transparent,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .background(Color.White, shape = RoundedCornerShape(10.dp))
                                .padding(5.dp)
                                .clip(RoundedCornerShape(10.dp))
                        ) {
                            NetworkImage(
                                modifier = Modifier
                                    .size(50.dp),
                                url = product.images[it]
                            )

                        }
                    }

                }
                Spacer(modifier = Modifier.height(50.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                        )
                    //   .padding(15.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(15.dp)
                        ) {
                            Text(
                                text = product.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )

                            Spacer(modifier = Modifier.height(25.dp))

                            Text(
                                text = "Description",//product.description,
                                fontSize = 16.sp,
                                color = MaterialTheme.colors.primary
                            )
                            Spacer(modifier = Modifier.height(25.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "See more Details",
                                    color = MaterialTheme.colors.primary,
                                    fontSize = 16.sp,

                                    )
                                Icon(
                                    painter = rememberVectorPainter(image = Icons.Default.ArrowForward),
                                    contentDescription = "",
                                    tint = MaterialTheme.colors.primary
                                )
                            }


                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Image(
                                painter = rememberVectorPainter(image = Icons.Default.Favorite),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Color.Red),
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        Color(0x75F44336),
                                        shape = RoundedCornerShape(
                                            topStart = 20.dp,
                                            bottomStart = 20.dp
                                        )
                                    )
                                    .padding(10.dp)
                                    .weight(1f)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color(0x8DB3B0B0),
                                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                            )
                            .padding(15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        LazyRow(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                            items(product.colors.size) {
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .border(
                                            width = 1.dp,
                                            color = if (colorSelected == product.colors[it]) MaterialTheme.colors.primary else Color.Transparent,
                                            shape = CircleShape
                                        )
                                        .padding(5.dp)
                                        .background(color = product.colors[it], shape = CircleShape)
                                        .clip(CircleShape)
                                        .clickable {
                                            colorSelected = product.colors[it]
                                        }
                                )
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    if (quantity > 1) {
                                        quantity--
                                    }
                                },
                                modifier = Modifier
                                    .background(color = Color.White, shape = CircleShape)
                                    .clip(CircleShape)

                            ) {
                                Image(
                                    painter = rememberVectorPainter(image = Icons.Default.Delete),
                                    contentDescription = "Remove Item"
                                )
                            }
                            Text(
                                text = quantity.toString(),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .width(35.dp)
                                    .wrapContentHeight()
                            )
                            IconButton(
                                onClick = {
                                    if (quantity < 5) {
                                        quantity++
                                    } else {
                                        // TODO, Show Toast, Cannot add more than 5

                                    }
                                },
                                modifier = Modifier
                                    .background(color = Color.White, shape = CircleShape)
                                    .clip(CircleShape)

                            ) {
                                Image(
                                    painter = rememberVectorPainter(image = Icons.Default.AddCircle),
                                    contentDescription = null
                                )
                            }
                        }
                    }


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(
                                Color.White,
                                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                            )
                            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primary,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .width(200.dp)
                                .padding(top = 30.dp, bottom = 30.dp)
                                .height(60.dp)
                                .clip(RoundedCornerShape(15.dp)),
                            onClick = {
                                /*Toast.makeText(
                                    context,
                                    "Successfully added to cart",
                                    Toast.LENGTH_SHORT
                                ).show()*/
                                // TODO, Add to Cart, Show Toast
                            },
                        ) {
                            Text(text = "Add to Cart", fontSize = 16.sp)
                        }
                    }
                }
            }

        } else {
            // Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
            // TODO, Show Toast, Error msg
        }
    }
}