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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
                    .padding(start = 6.dp, end = 25.dp)
                    .fillMaxWidth(),
                title = {
                    Text(
                        "Item",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }, enabled = false) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(width = 20.dp, height = 20.dp)
                        )
                    }
                },
                actions = {
                    Box {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Shopping Bag",
                            modifier = Modifier.size(width = 24.dp, height = 24.dp)
                        )
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(Color(0xFF67548B), CircleShape)
                                .align(Alignment.TopEnd)
                        ) {
                            Text(
                                text = "3",
                                color = Color.White,
                                fontSize = 8.sp,
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
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop,
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
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
                                .padding(16.dp)
                                .background(Color.Transparent)
                                .border(
                                    width = 1.dp,
                                    color = Color.Transparent,
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .padding(vertical = 5.dp)
                                .padding(end = 18.dp)
                                .padding(start = 13.dp),
                            verticalAlignment = CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color.Green,
                                modifier = Modifier
                                    .size(14.dp),

                                )
                            Text(
                                text = "In stock",
                                color = Color.Black,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(start = 6.dp)
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .padding(top = 14.dp)
                            .padding(bottom = 2.dp)
                    ) {
                        currentProduct?.title?.let {
                            Text(
                                text = it,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                )
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        currentProduct?.rating?.let {
                            Text(
                                text = it.toString(),
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                )
                            )
                            repeat(it) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(width = 11.dp, height = 11.dp)
                                        .align(alignment = Alignment.CenterVertically),
                                    tint = Color(0xFF67548B),
                                )
                            }
                        }
                    }
                    Text(
                        text = "Category: Home",
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = Color(0XFF4A4A4A)
                        )
                    )
                    currentProduct?.description?.let { description ->
                        Text(
                            modifier = Modifier
                                .padding(top = 23.dp)
                                .padding(bottom = 19.dp),
                            text = description,
                            style = TextStyle(
                                color = Color.DarkGray,
                                fontSize = 16.sp,
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
                                fontSize = 24.sp,
                            )
                        )
                    }
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .width(333.dp)
                            .padding(top = 52.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFF47337A))
                    ) {
                        Text(text = "+ Add to cart")
                    }
                }
            }
        }
    )
}