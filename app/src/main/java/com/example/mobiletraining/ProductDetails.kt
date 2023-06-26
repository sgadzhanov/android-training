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
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.firsttask.R
import com.example.mobiletraining.models.ProductModel
import com.example.mobiletraining.models.viewmodels.ProductViewModel
import com.example.mobiletraining.ui.theme.Black
import com.example.mobiletraining.ui.theme.Black80
import com.example.mobiletraining.ui.theme.Gray
import com.example.mobiletraining.ui.theme.Green
import com.example.mobiletraining.ui.theme.PurpleRatingStar
import com.example.mobiletraining.ui.theme.Violet
import com.example.mobiletraining.ui.theme.White
import com.example.mobiletraining.utils.UrlImage
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ProductDetails(modifier: Modifier = Modifier) {

    val viewModel: ProductViewModel = hiltViewModel()
    val response by viewModel.response.collectAsState()
    var currentProduct by remember { mutableStateOf<ProductModel?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getProduct()
    }

    LaunchedEffect(response) {
        response?.let {
            it.onSuccess { res -> currentProduct = res }
            it.onFailure { /* todo */ }
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
                    IconButton(onClick = { /*TODO*/ }, enabled = false) {
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
                                text = "3",
                                color = White,
                                fontSize = dimensionResource(id = R.dimen.FONT_SIZE_XS).value.sp,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            )
        }, content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(top = paddingValues.calculateTopPadding())
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
                            url = currentProduct?.image,
                            description = currentProduct?.short_description
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
                                modifier = Modifier
                                    .size(dimensionResource(id = R.dimen.SIZE_ICON_SMALL)),
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
                        currentProduct?.title?.let {
                            Text(
                                text = it,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = dimensionResource(id = R.dimen.FONT_SIZE_MEDIUM).value.sp
                                )
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        currentProduct?.rating?.let {
                            Text(
                                text = it.toString(),
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = dimensionResource(id = R.dimen.FONT_SIZE_MEDIUM).value.sp
                                )
                            )
                            repeat(it) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(dimensionResource(id = R.dimen.SIZE_ICON_XS))
                                        .align(alignment = Alignment.CenterVertically),
                                    tint = PurpleRatingStar,
                                )
                            }
                        }
                    }
                    currentProduct?.category?.let { category ->
                        Text(
                            text = stringResource(id = R.string.CATEGORY_MESSAGE) + category,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = dimensionResource(id = R.dimen.FONT_SIZE_SMALL).value.sp,
                                color = Gray,
                            )
                        )
                    }
                    currentProduct?.description?.let { description ->
                        Text(
                            modifier = Modifier
                                .padding(top = dimensionResource(id = R.dimen.PADDING_TOP_PRODUCT_DESCRIPTION))
                                .padding(bottom = dimensionResource(id = R.dimen.PADDING_BOTTOM_PRODUCT_DESCRIPTION)),
                            text = description,
                            style = TextStyle(
                                color = Black80,
                                fontSize = dimensionResource(id = R.dimen.FONT_SIZE_MEDIUM).value.sp,
                                textAlign = TextAlign.Left,
                            )
                        )
                    }
                    currentProduct?.price?.let { price ->
                        val formattedPrice: String = BigDecimal(price).setScale(2).toString()
                        Text(
                            text = "$$formattedPrice",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = dimensionResource(id = R.dimen.FONT_SIZE_LARGE).value.sp,
                            )
                        )
                    }
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
    )
}