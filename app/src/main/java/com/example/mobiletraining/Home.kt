package com.example.mobiletraining

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.firsttask.R
import com.example.mobiletraining.models.viewmodels.ProductsViewModel
import com.example.mobiletraining.ui.theme.Violet
import com.example.mobiletraining.utils.RatingStars
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(goToProductDetails: (id: String) -> Unit = {}) {
    val productsViewModel: ProductsViewModel = hiltViewModel()
    val coroutineScore = rememberCoroutineScope()
    var filterValue by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        coroutineScore.launch(Dispatchers.IO) {
            productsViewModel.filterProducts(filterValue)
            isLoading = false
        }
    }

    Scaffold(
        containerColor = Transparent,
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(end = dimensionResource(id = R.dimen.PADDING_XXXL)),
                title = {
                    Text(
                        text = stringResource(id = R.string.HOME),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                        fontSize = dimensionResource(id = R.dimen.FONT_SIZE_LARGE).value.sp,
                    )
                },
                actions = {
                    Icon(Icons.Outlined.AccountCircle, contentDescription = null)
                    Icon(
                        Icons.Outlined.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.PADDING_XXXL)),
                    )
                }
            )
        }, content = { paddingValues ->
            Box(
                modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
            ) {
                Image(
                    painter = painterResource(R.drawable.pd_background),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = stringResource(id = R.string.BACKGROUND_IMAGE_DESCRIPTION),
                    contentScale = ContentScale.Crop,
                )
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(dimensionResource(id = R.dimen.SIZE_MEDIUM)),
                        color = Violet,
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .padding(
                                start = dimensionResource(id = R.dimen.PADDING_XXL),
                                end = dimensionResource(id = R.dimen.PADDING_XXL),
                            )
                            .fillMaxSize()
                    ) {
                        SearchBar(productsViewModel = productsViewModel)
                        Row(
                            modifier = Modifier.padding(
                                top = dimensionResource(id = R.dimen.PADDING_XXL),
                                bottom = dimensionResource(id = R.dimen.PADDING_MEDIUM),
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = stringResource(id = R.string.NEW_PRODUCTS),
                                fontSize = dimensionResource(id = R.dimen.FONT_SIZE_MEDIUM_PLUS).value.sp,
                                modifier = Modifier.weight(1f),
                            )
                            Icon(
                                imageVector = Icons.Default.FilterList,
                                contentDescription = null,
                                modifier = Modifier.size(dimensionResource(id = R.dimen.SIZE_XXL)),
                            )
                        }
                        productsViewModel.filteredProducts.let { products ->
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {

                                items(products.size) { index ->
                                    val currentProduct = products[index]

                                    Box(modifier = Modifier
                                        .padding(
                                            top = dimensionResource(id = R.dimen.PADDING_LARGE_PLUS),
                                            bottom = dimensionResource(id = R.dimen.PADDING_XL),
                                        )
                                        .clickable { goToProductDetails(currentProduct.id.toString()) })
                                    {
                                        Row(
                                            modifier = Modifier
                                                .padding(top = dimensionResource(id = R.dimen.PADDING_LARGE))
                                        ) {
                                            AsyncImage(
                                                model = currentProduct.image,
                                                contentDescription = currentProduct.short_description,
                                                modifier = Modifier
                                                    .size(dimensionResource(id = R.dimen.PRODUCT_HOME_PAGE_IMAGE_SIZE))
                                                    .clip(
                                                        shape = RoundedCornerShape(
                                                            dimensionResource(id = R.dimen.SIZE_MEDIUM)
                                                        )
                                                    )
                                            )
                                            Column(
                                                modifier = Modifier
                                                    .padding(start = dimensionResource(id = R.dimen.PADDING_MEDIUM_PLUS))
                                            ) {
                                                Text(
                                                    text = "${stringResource(id = R.string.CATEGORY_TEMPLATE)}${currentProduct.category}",
                                                    style = MaterialTheme.typography.headlineSmall.copy(
                                                        color = Color.DarkGray,
                                                        fontSize = 12.sp,
                                                    ),
                                                )
                                                Text(
                                                    text = currentProduct.title,
                                                    style = MaterialTheme.typography.titleSmall.copy(
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = dimensionResource(id = R.dimen.FONT_SIZE_SMALL).value.sp,
                                                    )
                                                )
                                                Text(
                                                    modifier = Modifier.padding(
                                                        top = dimensionResource(id = R.dimen.PADDING_SMALL)
                                                    ),
                                                    text = currentProduct.short_description,
                                                    style = MaterialTheme.typography.bodySmall.copy(
                                                        fontWeight = FontWeight.Medium,
                                                        color = Color.DarkGray,
                                                        fontSize = dimensionResource(id = R.dimen.FONT_SIZE_SMALL).value.sp,
                                                    )
                                                )
                                                RatingStars(rating = currentProduct.rating)
                                                Text(
                                                    modifier = Modifier.padding(
                                                        top = dimensionResource(id = R.dimen.PADDING_SMALL)
                                                    ),
                                                    text = "${stringResource(id = R.string.DOLLAR_SIGN)}${currentProduct.price}",
                                                    style = MaterialTheme.typography.titleSmall,
                                                    fontSize = dimensionResource(id = R.dimen.FONT_SIZE_MEDIUM).value.sp,
                                                )
                                            }
                                        }
                                    }
                                    Divider(
                                        color = Gray,
                                        thickness = dimensionResource(id = R.dimen.DIVIDER),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(productsViewModel: ProductsViewModel) {
    var filterText: String by remember { mutableStateOf("") }

    TextField(
        value = filterText,
        onValueChange = { value ->
            filterText = value
            productsViewModel.filterProducts(value)
        },
        placeholder = { Text(text = stringResource(id = R.string.SEARCH)) },
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.SEARCH_FIELD_SHAPE))),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(id = R.dimen.SIZE_XL))
            )
        },
        singleLine = true,
        shape = MaterialTheme.shapes.small,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Transparent,
            disabledIndicatorColor = Transparent,
            unfocusedIndicatorColor = Transparent,
        )
    )
}