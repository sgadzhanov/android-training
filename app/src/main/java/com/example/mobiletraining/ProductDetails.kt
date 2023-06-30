package com.example.mobiletraining

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.firsttask.R
import com.example.mobiletraining.destinations.CartDestination
import com.example.mobiletraining.destinations.HomeDestination
import com.example.mobiletraining.models.ProductModel
import com.example.mobiletraining.models.viewmodels.ProductViewModel
import com.example.mobiletraining.ui.theme.Black
import com.example.mobiletraining.ui.theme.Black80
import com.example.mobiletraining.ui.theme.DarkGray
import com.example.mobiletraining.ui.theme.Green
import com.example.mobiletraining.ui.theme.PurpleRatingStar
import com.example.mobiletraining.ui.theme.Violet
import com.example.mobiletraining.ui.theme.White
import com.example.mobiletraining.utils.RatingStars
import com.example.mobiletraining.utils.UrlImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.mobiletraining.utils.asFormattedString

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetails(id: String, destinationsNavigator: DestinationsNavigator) {
    val viewModel: ProductViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(id) {
        coroutineScope.launch(Dispatchers.IO) {
            viewModel.getProduct(id)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_medium),
                        end = dimensionResource(id = R.dimen.padding_top_app_bar_end)
                    )
                    .fillMaxWidth(),
                title = {
                    Text(
                        text = stringResource(id = R.string.item_topbar),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { destinationsNavigator.navigate(HomeDestination) }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_icon_description),
                            modifier = Modifier.size(
                                width = dimensionResource(id = R.dimen.size_icon_medium),
                                height = dimensionResource(id = R.dimen.size_icon_medium)
                            )
                        )
                    }
                },
                actions = {
                    Box {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = stringResource(id = R.string.bag_icon_description),
                            modifier = Modifier
                                .size(
                                    width = dimensionResource(id = R.dimen.size_icon_large),
                                    height = dimensionResource(id = R.dimen.size_icon_large),
                                )
                                .clickable { destinationsNavigator.navigate(CartDestination) }
                        )
                        val productsCount = GlobalState.cartProducts.size
                        if (productsCount != 0) {
                            Box(
                                modifier = Modifier
                                    .size(dimensionResource(id = R.dimen.size_medium))
                                    .background(color = PurpleRatingStar, CircleShape)
                                    .align(Alignment.TopEnd)
                            ) {
                                Text(
                                    text = productsCount.toString(),
                                    color = White,
                                    fontSize = dimensionResource(id = R.dimen.font_size_xs).value.sp,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            )
        }, content = { paddingValues ->
            viewModel.product.value?.let { product: ProductModel ->
                Box(
                    modifier = Modifier
                        .padding(top = paddingValues.calculateTopPadding())
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(R.drawable.pd_background),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = stringResource(id = R.string.background_image_description),
                        contentScale = ContentScale.Crop,
                    )
                    Column(
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(id = R.dimen.padding_product_image_horizontal))
                            .fillMaxSize()
                    ) {
                        Box {
                            UrlImage(
                                url = product.image,
                                description = product.short_description
                            )
                            Row(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(dimensionResource(id = R.dimen.padding_xl))
                                    .background(Color.Transparent)
                                    .border(
                                        width = dimensionResource(id = R.dimen.width_tiny_border),
                                        color = Color.Transparent,
                                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_shape))
                                    )
                                    .background(
                                        color = White,
                                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_shape))
                                    )
                                    .padding(vertical = dimensionResource(id = R.dimen.padding_medium))
                                    .padding(end = dimensionResource(id = R.dimen.padding_xxl))
                                    .padding(start = dimensionResource(id = R.dimen.padding_large_plus)),
                                verticalAlignment = CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Green,
                                    modifier = Modifier.size(dimensionResource(id = R.dimen.size_icon_small)),
                                )
                                Text(
                                    text = stringResource(id = R.string.in_stock_message),
                                    color = Black,
                                    fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp,
                                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally)
                                .padding(top = dimensionResource(id = R.dimen.padding_large_plus))
                                .padding(bottom = dimensionResource(id = R.dimen.padding_xs))
                        ) {
                            Text(
                                text = product.title,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp
                                )
                            )
                            Spacer(Modifier.weight(1f))
                            RatingStars(rating = product.rating)
                        }
                        Text(
                            text = stringResource(id = R.string.category_message) + product.category,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = dimensionResource(id = R.dimen.font_size_small).value.sp,
                                color = DarkGray,
                            )
                        )

                        Text(
                            modifier = Modifier
                                .padding(top = dimensionResource(id = R.dimen.padding_top_product_description))
                                .padding(bottom = dimensionResource(id = R.dimen.padding_bottom_product_description)),
                            text = product.description,
                            style = TextStyle(
                                color = Black80,
                                fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp,
                                textAlign = TextAlign.Left,
                            )
                        )
                        val formattedPrice = product.price.asFormattedString()
                        Text(
                            text = "${stringResource(id = R.string.dollar_sign)}$formattedPrice",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = dimensionResource(id = R.dimen.font_size_xl).value.sp,
                            )
                        )
                        val productToAdd = ProductModel(
                            id = product.id,
                            title = product.title,
                            description = product.description,
                            short_description = product.short_description,
                            stock = product.stock,
                            price = product.price,
                            rating = product.rating,
                            image = product.image,
                            category = product.category,
                        )
                        Button(
                            onClick = { GlobalState.addProduct(productToAdd) },
                            modifier = Modifier
                                .width(dimensionResource(id = R.dimen.width_add_button))
                                .padding(top = dimensionResource(id = R.dimen.padding_top_add_button)),
                            colors = ButtonDefaults.buttonColors(Violet)
                        ) {
                            Text(text = stringResource(id = R.string.add_to_cart))
                        }
                    }
                }
            }
        }
    )
}