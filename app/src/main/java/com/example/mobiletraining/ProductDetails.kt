package com.example.mobiletraining

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.mobiletraining.destinations.HomeDestination
import com.example.mobiletraining.models.ProductModel
import com.example.mobiletraining.models.viewmodels.ProductViewModel
import com.example.mobiletraining.ui.theme.Black
import com.example.mobiletraining.ui.theme.Black80
import com.example.mobiletraining.ui.theme.DarkGray
import com.example.mobiletraining.ui.theme.Green
import com.example.mobiletraining.ui.theme.Violet
import com.example.mobiletraining.ui.theme.White
import com.example.mobiletraining.utils.RatingStars
import com.example.mobiletraining.utils.UrlImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.text.DecimalFormat

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
                        start = dimensionResource(id = R.dimen.PADDING_MEDIUM),
                        end = dimensionResource(id = R.dimen.PADDING_TOP_APP_BAR_END)
                    )
                    .fillMaxWidth(),
                title = {
                    Text(
                        text = stringResource(id = R.string.ITEM_TOPBAR),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { destinationsNavigator.navigate(HomeDestination) }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.BACK_ICON_DESCRIPTION),
                            modifier = Modifier.size(
                                width = dimensionResource(id = R.dimen.SIZE_ICON_MEDIUM),
                                height = dimensionResource(id = R.dimen.SIZE_ICON_MEDIUM)
                            )
                        )
                    }
                },
                actions = {
                    Box {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = stringResource(id = R.string.BAG_ICON_DESCRIPTION),
                            modifier = Modifier.size(
                                width = dimensionResource(id = R.dimen.SIZE_ICON_LARGE),
                                height = dimensionResource(id = R.dimen.SIZE_ICON_LARGE),
                            )
                        )
                        Box(
                            modifier = Modifier
                                .size(dimensionResource(id = R.dimen.SIZE_MEDIUM))
                                .background(Color(0xFF67548B), CircleShape)
                                .align(Alignment.TopEnd)
                        ) {
                            Text(
                                text = "3", //TODO
                                color = White,
                                fontSize = dimensionResource(id = R.dimen.FONT_SIZE_XS).value.sp,
                                modifier = Modifier.align(Alignment.Center)
                            )
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
                        contentDescription = stringResource(id = R.string.BACKGROUND_IMAGE_DESCRIPTION),
                        contentScale = ContentScale.Crop,
                    )
                    Column(
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(id = R.dimen.PADDING_PRODUCT_IMAGE_HORIZONTAL))
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
                                    .padding(dimensionResource(id = R.dimen.PADDING_XL))
                                    .background(Color.Transparent)
                                    .border(
                                        width = dimensionResource(id = R.dimen.WIDTH_TINY_BORDER),
                                        color = Color.Transparent,
                                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.ROUNDED_CORNER_SHAPE))
                                    )
                                    .background(
                                        color = White,
                                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.ROUNDED_CORNER_SHAPE))
                                    )
                                    .padding(vertical = dimensionResource(id = R.dimen.PADDING_MEDIUM))
                                    .padding(end = dimensionResource(id = R.dimen.PADDING_XXL))
                                    .padding(start = dimensionResource(id = R.dimen.PADDING_LARGE_PLUS)),
                                verticalAlignment = CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Green,
                                    modifier = Modifier.size(dimensionResource(id = R.dimen.SIZE_ICON_SMALL)),
                                )
                                Text(
                                    text = stringResource(id = R.string.IN_STOCK_MESSAGE),
                                    color = Black,
                                    fontSize = dimensionResource(id = R.dimen.FONT_SIZE_MEDIUM).value.sp,
                                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.PADDING_MEDIUM))
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally)
                                .padding(top = dimensionResource(id = R.dimen.PADDING_LARGE_PLUS))
                                .padding(bottom = dimensionResource(id = R.dimen.PADDING_XS))
                        ) {
                            Text(
                                text = product.title,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = dimensionResource(id = R.dimen.FONT_SIZE_MEDIUM).value.sp
                                )
                            )
                            Spacer(Modifier.weight(1f))
                            RatingStars(rating = product.rating)
                        }
                        Text(
                            text = stringResource(id = R.string.CATEGORY_MESSAGE) + product.category,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = dimensionResource(id = R.dimen.FONT_SIZE_SMALL).value.sp,
                                color = DarkGray,
                            )
                        )

                        Text(
                            modifier = Modifier
                                .padding(top = dimensionResource(id = R.dimen.PADDING_TOP_PRODUCT_DESCRIPTION))
                                .padding(bottom = dimensionResource(id = R.dimen.PADDING_BOTTOM_PRODUCT_DESCRIPTION)),
                            text = product.description,
                            style = TextStyle(
                                color = Black80,
                                fontSize = dimensionResource(id = R.dimen.FONT_SIZE_MEDIUM).value.sp,
                                textAlign = TextAlign.Left,
                            )
                        )
                        val formattedPrice = DecimalFormat("0.00").format(product.price)
                        Text(
                            text = "${stringResource(id = R.string.DOLLAR_SIGN)}$formattedPrice",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = dimensionResource(id = R.dimen.FONT_SIZE_LARGE).value.sp,
                            )
                        )
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .width(dimensionResource(id = R.dimen.WIDTH_ADD_BUTTON))
                                .padding(top = dimensionResource(id = R.dimen.PADDING_TOP_ADD_BUTTON)),
                            colors = ButtonDefaults.buttonColors(Violet)
                        ) {
                            Text(text = stringResource(id = R.string.ADD_TO_CART))
                        }
                    }
                }
            }
        }
    )
}