package com.example.mobiletraining.cart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.firsttask.R
import com.example.mobiletraining.GlobalState
import com.example.mobiletraining.destinations.HomeDestination
import com.example.mobiletraining.ui.theme.Black80
import com.example.mobiletraining.ui.theme.BrightPurple
import com.example.mobiletraining.ui.theme.DarkGray
import com.example.mobiletraining.ui.theme.DividerGray
import com.example.mobiletraining.ui.theme.Pink
import com.example.mobiletraining.ui.theme.Violet
import com.example.mobiletraining.utils.asFormattedString
import com.example.mobiletraining.utils.constants.Constants.Shared.divider_opacity
import com.example.mobiletraining.utils.constants.Constants.Shared.spacer_weight
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun Cart(destinationsNavigator: DestinationsNavigator) {
    val openDialog = remember { mutableStateOf(false) }

    fun closeDialog() {
        openDialog.value = false
    }

    Scaffold(
        containerColor = Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.padding_xxl)),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_xl)),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Transparent),
                title = {
                    Text(
                        text = stringResource(id = R.string.cart_top_app_bar),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = dimensionResource(id = R.dimen.font_size_large).value.sp,
                    )
                }, navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        tint = DarkGray,
                        contentDescription = null,
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.size_icon_medium))
                            .clickable { destinationsNavigator.navigateUp() }
                    )
                }
            )
        }, content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .paint(
                        painter = painterResource(id = R.drawable.pd_background),
                        contentScale = ContentScale.FillBounds
                    )
                    .padding(top = paddingValues.calculateTopPadding())
                    .padding(
                        start = dimensionResource(id = R.dimen.size_xxxl),
                        end = dimensionResource(id = R.dimen.size_xxxl),
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (GlobalState.cartProducts.size == 0) {
                    Column(modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.empty_cart_bottom_padding))) {
                        Icon(
                            painterResource(id = R.drawable.shopping_cart),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                            tint = Pink,
                            contentDescription = null,
                        )
                        Text(
                            text = stringResource(id = R.string.empty_cart_message),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = dimensionResource(id = R.dimen.padding_medium)),
                            textAlign = TextAlign.Center,
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        val productsMap =
                            GlobalState.cartProducts.groupBy { it }.mapValues { it.value.size }

                        for ((product, count) in productsMap) {
                            Row(
                                modifier = Modifier
                                    .padding(
                                        top = dimensionResource(id = R.dimen.padding_xl),
                                        bottom = dimensionResource(id = R.dimen.padding_xxl)
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                AsyncImage(
                                    model = product.image,
                                    modifier = Modifier
                                        .size(dimensionResource(id = R.dimen.cart_image_size))
                                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.padding_medium))),
                                    contentScale = ContentScale.Fit,
                                    alignment = Alignment.Center,
                                    contentDescription = product.short_description,
                                )
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = dimensionResource(id = R.dimen.padding_medium))
                                ) {
                                    Text(
                                        text = product.title,
                                        fontSize = dimensionResource(id = R.dimen.font_size_small_plus).value.sp,
                                        color = Black80,
                                    )
                                    val price = product.price.asFormattedString()
                                    val formattedPrice =
                                        "${stringResource(id = R.string.dollar_sign)}${price}"
                                    Text(
                                        text = formattedPrice,
                                        fontSize = dimensionResource(id = R.dimen.font_size_small_plus).value.sp,
                                        color = Black80,
                                    )
                                    Text(
                                        text = "${stringResource(id = R.string.cart_item_quantity)} $count",
                                        fontSize = dimensionResource(id = R.dimen.font_size_small).value.sp,
                                        color = Black80,
                                    )
                                }
                                Column(
                                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .padding(
                                                top = dimensionResource(id = R.dimen.padding_medium),
                                                start = dimensionResource(id = R.dimen.padding_medium)
                                            )
                                            .size(dimensionResource(id = R.dimen.size_icon_medium))
                                            .clickable { GlobalState.removeProduct(product.id) },
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = null,
                                        tint = DarkGray,
                                    )
                                }
                            }
                            Divider(
                                color = DividerGray.copy(alpha = divider_opacity),
                                thickness = dimensionResource(id = R.dimen.divider),
                            )
                        }
                        Row(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_xl))) {
                            val formattedText =
                                "${stringResource(id = R.string.cart_items_count)} ${GlobalState.cartProducts.size}"
                            Text(
                                text = formattedText,
                                fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp,
                                color = Black80,
                            )
                            Spacer(modifier = Modifier.weight(divider_opacity))
                            val price =  GlobalState.cartProducts.sumOf { it.price }.asFormattedString()
                            val formattedSum = "${stringResource(id = R.string.cart_total_sum)} ${stringResource(id = R.string.dollar_sign)}${price}"
                            Text(
                                text = formattedSum,
                                fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp,
                                color = Black80,
                            )
                        }
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.weight(spacer_weight))
                            Button(
                                enabled = GlobalState.cartProducts.isNotEmpty(),
                                onClick = { openDialog.value = true },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Violet,
                                ),
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .padding(bottom = dimensionResource(id = R.dimen.padding_cart_button)),

                                ) {
                                Text(text = stringResource(id = R.string.checkout_button))
                            }
                        }
                        if (openDialog.value) {
                            AlertDialog(
                                modifier = Modifier
                                    .padding(bottom = dimensionResource(id = R.dimen.padding_cart_button))
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(dimensionResource(id = R.dimen.padding_large)),
                                onDismissRequest = { openDialog.value = false },
                                title = {
                                    Text(
                                        text = stringResource(id = R.string.confirm_modal_title),
                                        fontSize = dimensionResource(id = R.dimen.font_size_large).value.sp,
                                    )
                                },
                                text = {
                                    Text(
                                        text = stringResource(id = R.string.confirm_modal_subtitle),
                                        fontSize = dimensionResource(id = R.dimen.font_size_medium_plus).value.sp,
                                    )
                                },
                                dismissButton = {
                                    Button(
                                        onClick = { openDialog.value = false },
                                        colors = ButtonDefaults.buttonColors(
                                            contentColor = Violet,
                                            containerColor = Transparent,
                                        )
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.modal_no),
                                            fontSize = dimensionResource(id = R.dimen.font_size_medium_plus).value.sp,
                                            color = BrightPurple,
                                        )
                                    }
                                },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            closeDialog()
                                            GlobalState.placeOrder()
                                            destinationsNavigator.navigate(HomeDestination)
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            contentColor = Violet,
                                            containerColor = Transparent,
                                        )
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.modal_yes),
                                            fontSize = dimensionResource(id = R.dimen.font_size_medium_plus).value.sp,
                                            color = BrightPurple,
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}